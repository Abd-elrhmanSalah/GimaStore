package com.gima.gimastore.entity.storePartDist;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "STORE_PARTS_DIST")
public class StoresPartDist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID_FROM")
    private Store storeFrom;

    @ManyToOne
    @JoinColumn(name = "STORE_ID_TO")
    private Store storeTo;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "ACTION_BY")
    @Nullable
    private User actionBy;

    @ManyToOne
    @JoinColumn(name = "Dist_BY")
    private User distBy;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @Column(name = "ACTION_DATE")
    @Nullable
    private Date actionDate;

    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    @Column(name = "NOTES")
    @Nullable
    @Nationalized
    private String notes;

}
