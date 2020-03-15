package com.flower.father.mapper;

import com.flower.father.model.po.CustomerOrderPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerOrderMapper {
    @Insert({ "insert into tb_customer_order(order_type, external_order_id, customer_source" +
            ", customer_name, customer_tel, delivery_address, amount, freight, florist, remarks, state, order_created_at, delivery_at) " +
            "values(#{orderType}, #{externalOrderId}, #{customerSource}, #{customerName}, #{customerTel}, #{deliveryAddress}" +
            ", #{amount}, #{freight}, #{florist}, #{remarks}, #{state}, #{orderCreatedAt}, #{deliveryAt})" })
    @Options(useGeneratedKeys = true)
    Long insert(CustomerOrderPo po);

    @Select({ "select * from tb_customer_order" })
    @Results({
            @Result(property = "orderType",  column = "order_type"),
            @Result(property = "externalOrderId", column = "external_order_id"),
            @Result(property = "customerSource", column = "customer_source"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "customerTel", column = "customer_tel"),
            @Result(property = "deliveryAddress", column = "delivery_address"),
            @Result(property = "orderCreatedAt", column = "order_created_at"),
            @Result(property = "deliveryAt", column = "delivery_at")
    })
    List<CustomerOrderPo> searchList();

    @Select({ "select * from tb_customer_order where id = #{id}" })
    @Results({
            @Result(property = "orderType",  column = "order_type"),
            @Result(property = "externalOrderId", column = "external_order_id"),
            @Result(property = "customerSource", column = "customer_source"),
            @Result(property = "customerName", column = "customer_name"),
            @Result(property = "customerTel", column = "customer_tel"),
            @Result(property = "deliveryAddress", column = "delivery_address"),
            @Result(property = "orderCreatedAt", column = "order_created_at"),
            @Result(property = "deliveryAt", column = "delivery_at")
    })
    CustomerOrderPo searchById(Long id);

    @Update({ "update tb_customer_order set state = #{state} where id = #{id}" })
    Integer updateState(CustomerOrderPo po);

    @Update({ "update tb_customer_order set florist = #{florist} where id = #{id}" })
    Integer updateFlorist(CustomerOrderPo po);
}
