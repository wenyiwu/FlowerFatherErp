package com.flower.father.mapper.service;

import com.flower.father.mapper.PurchaseGoodMapper;
import com.flower.father.mapper.PurchaseOrderMapper;
import com.flower.father.model.po.PurchaseGoodPo;
import com.flower.father.model.po.PurchaseOrderPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eiven
 */
@Service
public class PurchaseOrdersService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseGoodMapper purchaseGoodMapper;


    public Long insertPurchaseGood(PurchaseGoodPo po) {
        return purchaseGoodMapper.insert(po);
    }

    public Long insertPurchaseOrder(PurchaseOrderPo po) {
        return purchaseOrderMapper.insert(po);
    }

    public List<PurchaseOrderPo> searchAllPurchaseOrder() {
        return purchaseOrderMapper.searchList();
    }

    public List<PurchaseGoodPo> searchPurchaseGoods(Long orderId) {
        return purchaseGoodMapper.searchList(orderId);
    }
}
