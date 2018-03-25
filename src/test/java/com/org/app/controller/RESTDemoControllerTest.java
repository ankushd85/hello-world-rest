package com.org.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.org.app.Order;
import com.org.app.OrderedRunner;
import com.org.app.beans.User;

import reactor.core.publisher.Mono;

/**
 * @author pfe456
 * 
 */
@RunWith(OrderedRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RESTDemoControllerTest {

	private static final String NAME = "Scott Peterson";
	private static final String MODIFIED_NAME = "Scott Jr";
	
	private static User user = new User(10898787,NAME);
	private static User userNoName = new User(19987654,null);
	private static User userNotPresent = new User(1089898765,NAME);
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private WebTestClient webClient;

	/**
	 * POST api test for adding User in database
	 */
	@Test
	@Order(order = 1)
	public void postUserTest() {
		User body = this.restTemplate.postForObject("/myorg/mylob/helloworld/v1/users",user, User.class);
		assertThat(body.getId()>0);
		user.setId(body.getId());
	}
	
	/**
	 * PUT api test for updating User based on used id
	 */
	@Test
	@Order(order = 2)
	public void putUserTest() {
		user.setName(MODIFIED_NAME);
		this.webClient.put().uri("/myorg/mylob/helloworld/v1/users/"+user.getId()).body(Mono.just(user),User.class)
			.exchange().expectStatus().is2xxSuccessful();
	}
	
	/**
	 * GET api test for fetching the users from database based on use id
	 */
	@Test
	@Order(order = 3)
	public void getUserTest() {
		String body = this.restTemplate.getForObject("/myorg/mylob/helloworld/v1/users/"+user.getId(), String.class);
		assertThat(body).contains(MODIFIED_NAME);
	}
	
	/**
	 * GET api test for fetching all the users from database
	 */
	@Test
	@Order(order = 4)
	public void getUsersTest() {
		String body = this.restTemplate.getForObject("/myorg/mylob/helloworld/v1/users", String.class);
		assertThat(body).contains(MODIFIED_NAME);
	}
	
	/**
	 * POST api test for deleting User present in database
	 */
	@Test
	@Order(order = 5)
	public void deleteUserTest() {
		this.webClient.delete().uri("/myorg/mylob/helloworld/v1/users/"+user.getId())
			.exchange().expectStatus().is2xxSuccessful();
	}
	
	/**
	 * DELETE api test for deleting User not present in database
	 */
	@Test
	@Order(order = 6)
	public void getUserPostDeleteTest() {
		this.webClient.get().uri("/myorg/mylob/helloworld/v1/users/"+user.getId())
		.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * POST api test for adding User with blank user name
	 */
	@Test
	@Order(order = 7)
	public void postUserNoNameTest() {
		this.webClient.post().uri("/myorg/mylob/helloworld/v1/users/").body(Mono.just(userNoName),User.class)
		.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * POST api test for adding User with null user
	 */
	@Test
	@Order(order = 8)
	public void postUserNullTest() {
		this.webClient.post().uri("/myorg/mylob/helloworld/v1/users/")
		.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * PUT api test for updating user with user id not present in database
	 */
	@Test
	@Order(order = 9)
	public void putUserNotPresentTest() {
		user.setName(MODIFIED_NAME);
		this.webClient.put().uri("/myorg/mylob/helloworld/v1/users/"+userNotPresent.getId()).body(Mono.just(userNotPresent),User.class)
			.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * DELETE api test for User Id not present in database
	 */
	@Test
	@Order(order = 10)
	public void deleteUserNotPresentTest() {
		this.webClient.delete().uri("/myorg/mylob/helloworld/v1/users/"+userNotPresent.getId())
			.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * DELETE api test for no User Id
	 */
	@Test
	@Order(order = 11)
	public void deleteUserNoUserIdTest() {
		this.webClient.delete().uri("/myorg/mylob/helloworld/v1/users/")
			.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * GET api test for non int User Id
	 */
	@Test
	@Order(order = 12)
	public void getUserNonIntUserIdTest() {
		this.webClient.get().uri("/myorg/mylob/helloworld/v1/users/jhhj")
		.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * GET api test for User Id with negative value
	 */
	@Test
	@Order(order = 13)
	public void getUserWUserIdNegativeTest() {
		this.webClient.get().uri("/myorg/mylob/helloworld/v1/users/-1")
			.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * DELETE api test for User Id with negative value
	 */
	@Test
	@Order(order = 14)
	public void deleteUserWUserIdNegativeTest() {
		this.webClient.delete().uri("/myorg/mylob/helloworld/v1/users/-6")
			.exchange().expectStatus().is4xxClientError();
	}
	
	/**
	 * PUT api test for User Id with negative value
	 */
	@Test
	@Order(order = 15)
	public void updateUserWUserIdNegativeTest() {
		this.webClient.put().uri("/myorg/mylob/helloworld/v1/users/-988").body(Mono.just(user),User.class)
		.exchange().expectStatus().is4xxClientError();
	}
}
