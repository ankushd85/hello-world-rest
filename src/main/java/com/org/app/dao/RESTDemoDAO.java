package com.org.app.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.org.app.beans.User;

/**
 * @author pfe456
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RESTDemoDAO {
	
	// Dummy database. Initialize with some dummy values.
	private static Map<Long, User> users;
	{
		users = new HashMap<Long, User>();
		users.put(101l, new User(101, "Tom Cruise"));
		users.put(202l, new User(202, "Tylor Swift"));
		users.put(303l, new User(303, "Ankush Dhameeja"));
	}
	
	/**
	 * Returns map of all users from dummy database.
	 * 
	 * @return map of users
	 */
	public Collection<User> get() {
		return users.values();
	}
	
	/**
	 * Return User object for given id from dummy database. If User is
	 * not found, returns null.
	 * 
	 * @param id
	 *            User id
	 * @return User object for given id
	 */
	public User get(long userId) {
		return users.get(userId);
		
	}
	
	/**
	 * Create new User in dummy database. Updates the userId and insert new
	 * User in map.
	 * 
	 * @param user
	 *            User object
	 * @return User object with updated id
	 */
	public User create(User user) {
		user.setId(System.currentTimeMillis());
		users.put(user.getId(),user);
		return user;
	}
	
	/**
	 * Delete the User object from dummy database. If User not found for
	 * given id, returns null.
	 * 
	 * @param id
	 *            the User id
	 * @return id of deleted User object
	 */
	public Long delete(Long id) {
	
		if(null != users.remove(id)){
			return id;
		}else{
			return null;
		}
	}
	
	/**
	 * Update the User object for given id in dummy database. If User
	 * not exists, returns null
	 * 
	 * @param id
	 * @param user
	 * @return User object with id
	 */
	public User update(Long id, User user) {
		if(null != users.get(id)){
			user.setId(id);
			users.put(id, user);
			return user;
		}else{
			return null;
		}
	}
}
