package com.dal.catmeclone.notification;

import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public interface NotificationService {

    public void sendNotificationToNewuser(User user, String password, Course course);

    public void sendNotificationForPassword(String BannerId, String password, String sendto);

    public boolean isStatus();

}
