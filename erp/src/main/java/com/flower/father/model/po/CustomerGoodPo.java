package com.flower.father.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerGoodPo {
    private Long id;

    private Long orderId;

    private Long materialId;

    private Integer number;

    private Float price;
}
