package com.gima.gimastore.entity.supplyProcessPartDist;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
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
@Table(name = "SUPPLY_PROCESS_PARTS_DIST")
public class SupplyProcessPartDist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SUPPLYPROCESSPART_ID")
    private SupplyProcessPart supplyProcessPart;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

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
