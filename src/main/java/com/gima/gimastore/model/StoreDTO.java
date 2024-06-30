package com.gima.gimastore.model;

import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class StoreDTO implements Serializable {

    private Long id;
    private String storeName;
    private User user;
    private Boolean isLocked=false;

}
