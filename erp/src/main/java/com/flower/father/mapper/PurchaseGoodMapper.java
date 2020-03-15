package com.flower.father.mapper;

import com.flower.father.model.po.CustomerGoodPo;
import com.flower.father.model.po.PurchaseGoodPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PurchaseGoodMapper {

    @Insert({ "insert into tb_purchase_good(order_id, material_id, number, price) values(#{orderId}, #{materialId}, #{number}, #{price})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(PurchaseGoodPo po);

    @Select({ "select * from tb_purchase_good where order_id = #{orderId}" })
    @Results({
            @Result(property = "orderId",  column = "order_id"),
            @Result(property = "materialId", column = "material_id")
    })
    List<PurchaseGoodPo> searchList(Long orderId);

//    @Insert({ "insert into purchase_goods(purchase_order_id, goods_id, number) values(#{purchaseOrderId}, #{goodsId}, #{number})" })
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    PurchaseGoodPo select(PurchaseGoodPo po);
}
