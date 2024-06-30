package com.gima.gimastore.model;

import com.gima.gimastore.entity.Notification;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class NotificationResponse {

    private Integer totalUnread;
    private List<Notification> notifications;


}
