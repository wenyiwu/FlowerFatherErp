package com.flower.father.model.po;

import lombok.Data;

/**
 * @author eiven
 */
@Data
public class RawMaterialPo {
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
}
