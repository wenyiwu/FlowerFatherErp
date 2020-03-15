package com.flower.father.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author eiven
 */
@Data
@NoArgsConstructor
public class PurchaseOrderResponse {
    private Long id;

    private String provider;

    private String remarks;

    private Float amount;

    private Float freight;

    private String orderCreatedAt;

    private String createdAt;

    private List<PurchaseGoodResponse> goods;



    public PurchaseOrderResponse(Long id) {
        this.id = id;
        this.provider = "1";
        this.remarks = "12";
        this.amount = 10.0f;
        this.orderCreatedAt = "123";
        this.createdAt = "1234";
        this.freight = 123.0f;
        PurchaseGoodResponse good1 = new PurchaseGoodResponse(101L);
        PurchaseGoodResponse good2 = new PurchaseGoodResponse(102L);
        this.goods = Lists.newArrayList(good1, good2);
    }
}
