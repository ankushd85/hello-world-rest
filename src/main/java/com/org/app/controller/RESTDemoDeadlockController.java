package com.org.app.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.app.dao.RESTDemoDAO;

/**
 * @author pfe456
 * Main Controller for creating and detecting deadlocks
 */
@RestController
@RequestMapping("/myorg/mylob/helloworld/v1/deadlocks")
public class RESTDemoDeadlockController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RESTDemoDeadlockController.class);

	/**
	 * This method detects current deadlock thread count and returns it to user as response
	 * @return
	 * 		ResponseEntity<String> with HTTP 200 
	 */
	@GetMapping()
	public ResponseEntity<String> getCurrentDeadlocks(){
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
	    long[] deadlockThreadIds = threadBean.findDeadlockedThreads();
	    int deadlockedThreadsCount = deadlockThreadIds != null? deadlockThreadIds.length : 0;
	    System.out.println("Number of deadlocked threads: " + deadlockedThreadsCount);
		
		return new ResponseEntity<String>("Number of threads in deadlock " + deadlockedThreadsCount + "  ", HttpStatus.OK);
	}
	
	/**
	 * This method creates 2 threads with race condition to demonstrate thread deadlock. Deadlock lasts for 10 seconds
	 * @return
	 * 		ResponseEntity<String> with HTTP 200 
	 */		
	@PostMapping()
	public ResponseEntity<String> createDeadlock(){
		
		// DAO Objects to be locked
		final RESTDemoDAO object1 = new RESTDemoDAO();
		final RESTDemoDAO object2 = new RESTDemoDAO();
		
		// Lock used for default timeouts
		final Lock lock1 = new ReentrantLock();
		final Lock lock2 = new ReentrantLock();
		
		Thread thread1 = new Thread(new Runnable() {
	        public void run() {
	        	try {
					if(lock1.tryLock() ){  // Using lock1, lock DAO object1
			            
						synchronized (object1) {
			                System.out.println("Thread1 acquired lock1");
			                try {
			                    TimeUnit.MILLISECONDS.sleep(50);
			                } catch (InterruptedException e) {
			                	LOG.error("RESTDemoDeadlockController: Error occured during thread 1 sleep :" + e.getMessage());
			                }
			                
			                try {
								if(lock2.tryLock(10L,TimeUnit.SECONDS) ){  // Using lock2, lock DAO object2 with default timeout of 10 secs
									try{
										synchronized (object2) {
								            System.out.println("Thread2 acquired lock1");
								        }
								    } finally{
								    	lock2.unlock();
								    }
								}else{
									 System.out.println("Thread1 giving up on acquiring lock on lock2");
								}
							} catch (InterruptedException e) {
								LOG.error("RESTDemoDeadlockController: Error occured during thread 1 lock2 :" + e.getMessage());
							}
			            }
						
					}
	        	}finally {
	        		lock1.unlock();
				}
	        }
	 
	    });
	    thread1.start();
	 
	    Thread thread2 = new Thread(new Runnable() {
	        public void run() {
	        	try {
					if(lock2.tryLock() ){  // Using lock2, lock DAO object2 
						
			            synchronized (object2) {
			                System.out.println("Thread2 acquired lock2");
			                try {
			                    TimeUnit.MILLISECONDS.sleep(50);
			                } catch (InterruptedException e) {
			                	LOG.error("RESTDemoDeadlockController: Error occured during thread 2 sleep :" + e.getMessage());
			                }
			                
			                try {
								if(lock1.tryLock(10L,TimeUnit.SECONDS) ){  // Using lock1, lock DAO object1 with default timeout of 10 secs
									try{
										synchronized (object1) {
								            System.out.println("Thread2 acquired lock1");
								        }
								    } finally{
								    	lock1.unlock();
								    }
								}else{
									System.out.println("Thread2 giving up on acquiring lock on lock1");
								}
							} catch (InterruptedException e) {
								LOG.error("RESTDemoDeadlockController: Error occured during thread 1 lock1 :" + e.getMessage());
							}
			            }
					}
	        	}finally{
	        		lock2.unlock();
	        	}
	        }
	    });
	    thread2.start();
	    
		return new ResponseEntity<String>("Deadlock Created. ", HttpStatus.OK);
	}
}
