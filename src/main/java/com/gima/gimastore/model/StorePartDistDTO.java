package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class StorePartDistDTO implements Serializable {

    private Long id;

    private Store storeFrom;

    private Store storeTo;

    private Part part;

    private Integer amount;

    private User actionBy;

    private User distBy;

    private Status status;

    private Date actionDate;

    private Date creationDate;

    private String notes;

}
