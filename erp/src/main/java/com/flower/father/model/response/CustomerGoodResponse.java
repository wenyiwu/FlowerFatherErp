package com.flower.father.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eiven
 */
@Data
@NoArgsConstructor
public class CustomerGoodResponse {
    private Long goodId;

    private String name;

    private String level;

    private String classify;

    private String colour;

    private Integer number;

    private Float price;

    public CustomerGoodResponse(Long goodId) {
        this.goodId = goodId;
        this.name = "1";
        this.level = "2";
        this.classify = "3";
        this.colour = "4";
        this.number = 5;
        this.price = 6.0f;
    }
}
