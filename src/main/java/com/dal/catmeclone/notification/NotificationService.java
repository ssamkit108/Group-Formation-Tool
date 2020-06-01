/**
 * 
 */
package com.dal.catmeclone.notification;


/**
 * @author Mayank
 *
 */
public interface NotificationService {
	
	public void send(String from,String password,String to,String sub,String msg);

}
