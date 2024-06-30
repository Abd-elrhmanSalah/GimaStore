package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class StorePartSettlementDTO implements Serializable {
    private Long id;
    private Part part;
    private Store store;
    private Integer amountPrevious;
    private Integer amountUpdate;
    private Integer amountDiff;
    private User createdBy;
    private Date creationDate;
}
