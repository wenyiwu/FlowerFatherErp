package com.flower.father.mapper;

import com.flower.father.model.po.CustomerGoodPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerGoodMapper {

    @Insert({ "insert into tb_customer_good(order_id, material_id, number, price) values(#{orderId}, #{materialId}, #{number}, #{price})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(CustomerGoodPo po);

    @Select({ "select * from tb_customer_good where order_id = #{orderId}" })
    @Results({
            @Result(property = "orderId",  column = "order_id"),
            @Result(property = "materialId", column = "material_id")
    })
    List<CustomerGoodPo> searchList(Long orderId);

//    @Insert({ "insert into purchase_goods(purchase_order_id, goods_id, number) values(#{purchaseOrderId}, #{goodsId}, #{number})" })
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    PurchaseGoodPo select(PurchaseGoodPo po);
}
