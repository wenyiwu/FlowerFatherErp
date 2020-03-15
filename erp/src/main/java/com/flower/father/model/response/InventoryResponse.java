package com.flower.father.model.response;

import lombok.Data;

/**
 * @author eiven
 */
@Data
public class InventoryResponse {

    private Long id;

    private String name;

    private String level;

    private String classify;

    private String colour;

    /**
     * 库存
     */
    private Integer number;

    /**
     * 预支出库存
     */
    private Integer presellNumber;

    /**
     * 真实库存
     */
    private Integer realNumber;

    /**
     * 售量
     */
    private Integer salesNumber;

    public InventoryResponse() {
        this.id = 10L;
        this.name = "hha";
        this.level = "1";
        this.classify = "1";
        this.colour = "1";
        this.number = 100;
        this.presellNumber = 100;
        this.realNumber = 100;
        this.salesNumber = 100;
    }
}
