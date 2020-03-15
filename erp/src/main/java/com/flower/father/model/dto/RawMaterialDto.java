package com.flower.father.model.dto;

import lombok.Data;

/**
 * @author eiven
 */
@Data
public class RawMaterialDto {
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
     * 库存变化
     */
    private Integer numberChange;

    /**
     * 预支出库存
     */
    private Integer presellNumber;

    /**
     * 预支出库存变化
     */
    private Integer presellNumberChange;

    /**
     * 真实库存
     */
    private Integer realNumber;

    /**
     * 真实库存变化
     */
    private Integer realNumberChange;

    /**
     * 售量
     */
    private Integer salesNumber;

    /**
     * 售量变化
     */
    private Integer salesNumberChange;
}
