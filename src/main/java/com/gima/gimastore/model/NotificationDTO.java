package com.gima.gimastore.model;

import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class NotificationDTO implements Serializable {
    private Long id;
    private String title;
    private String message;
    private String privilege;
    private String readBy;
    private User createdBy;
    private Date creationDate = new Date();

    private String routeName;
    private Long receiver;


}
