package com.flower.father.mapper.service;

import com.flower.father.mapper.CustomerGoodMapper;
import com.flower.father.mapper.CustomerOrderMapper;
import com.flower.father.model.po.CustomerGoodPo;
import com.flower.father.model.po.CustomerOrderPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrdersService {

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @Autowired
    private CustomerGoodMapper customerGoodMapper;


    public Long insertCustomerGood(CustomerGoodPo po) {
        return customerGoodMapper.insert(po);
    }

    public Long insertCustomerOrder(CustomerOrderPo po) {
        return customerOrderMapper.insert(po);
    }

    public void updateState(CustomerOrderPo po) {
        customerOrderMapper.updateState(po);
    }

    public void updateFlorist(CustomerOrderPo po) {
        customerOrderMapper.updateFlorist(po);
    }

    public List<CustomerOrderPo> searchAllCustomerOrder() {
        return customerOrderMapper.searchList();
    }

    public List<CustomerGoodPo> searchCustomerGoods(Long orderId) {
        return customerGoodMapper.searchList(orderId);
    }

    public CustomerOrderPo searchCustomerOrder(Long orderId) {
        return customerOrderMapper.searchById(orderId);
    }

}
