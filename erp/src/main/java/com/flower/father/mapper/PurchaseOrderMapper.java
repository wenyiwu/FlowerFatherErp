package com.flower.father.mapper;

import com.flower.father.model.po.PurchaseOrderPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PurchaseOrderMapper {
    @Insert({ "insert into tb_purchase_order(amount, freight, provider, state, remarks, order_created_at) values(#{amount}, #{freight}, #{provider}, #{state}, #{remarks}, #{orderCreatedAt})" })
    @Options(useGeneratedKeys = true)
    Long insert(PurchaseOrderPo po);

    @Select({ "select * from tb_purchase_order" })
    @Results({
            @Result(property = "createdAt",  column = "created_at"),
            @Result(property = "orderCreatedAt", column = "order_created_at")
    })
    List<PurchaseOrderPo> searchList();

    @Update({ "update tb_purchase_order set state = #{state} where id = #{id}" })
    Integer update(PurchaseOrderPo po);
}
