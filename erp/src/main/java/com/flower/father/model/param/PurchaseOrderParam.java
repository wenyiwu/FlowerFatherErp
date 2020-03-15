package com.flower.father.model.param;

import com.flower.father.model.dto.PurchaseGoodDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author eiven
 */
@Data
public class PurchaseOrderParam {
    private Float amount;

    private Float freight;

    private String remarks;

    private String provider;

    private String createdAt;

    private List<PurchaseGoodParam> goods;
}
