package com.gima.gimastore.model;

import com.gima.gimastore.entity.Notification;

import java.util.List;

public class NotificationResponse {
    private Integer totalUnread;
    private List<Notification> notifications;

    public Integer getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(Integer totalUnread) {
        this.totalUnread = totalUnread;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
