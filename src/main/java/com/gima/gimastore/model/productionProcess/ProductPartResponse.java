package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.model.supplyProcess.StoreAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductPartResponse implements Serializable {

    @Nullable
    private Long id;
    private Part part;
    private Integer requestedAmount;
    private List<StoreAmount> stores;

}
