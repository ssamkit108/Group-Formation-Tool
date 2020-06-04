/**
 * 
 */
package com.dal.catmeclone.notification;

import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

/**
 * @author Mayank
 *
 */
public interface NotificationService {
	
	public void sendNotificationToNewuser(User user, Course course);

}
