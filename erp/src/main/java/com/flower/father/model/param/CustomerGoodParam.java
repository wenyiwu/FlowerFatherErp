package com.flower.father.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerGoodParam {
    private Long id;

    private Integer number;

    private Float price;

    public CustomerGoodParam(Long id) {
        this.id = id;
        this.number = 1;
        this.price = 2.0f;
    }
}
