package com.flower.father.service;

import com.flower.father.enums.CustomerOrderStatusEnum;
import com.flower.father.mapper.service.CustomerOrdersService;
import com.flower.father.model.dto.CustomerGoodDto;
import com.flower.father.model.dto.CustomerOrderDto;
import com.flower.father.model.dto.PurchaseOrderDto;
import com.flower.father.model.dto.RawMaterialDto;
import com.flower.father.model.po.CustomerGoodPo;
import com.flower.father.model.po.CustomerOrderPo;
import com.flower.father.model.po.PurchaseGoodPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author eiven
 */
@Service
public class CustomerOrdersCoreService {

    @Autowired
    private CustomerOrdersService customerOrdersService;

    @Autowired
    private RawMaterialCoreService rawMaterialCoreService;

    @Transactional(rollbackFor = Exception.class)
    public void createCustomerOrder(CustomerOrderDto dto) {
        CustomerOrderPo orderPo = buildCustomerOrderPo(dto);
        customerOrdersService.insertCustomerOrder(orderPo);
        dto.setId(orderPo.getId());
        for (CustomerGoodDto goodDto : dto.getGoods()) {
            CustomerGoodPo goodPo = buildCustomerGood(goodDto, orderPo);
            customerOrdersService.insertCustomerGood(goodPo);
        }

        //必须放最后面
        rawMaterialCoreService.updateNumber(dto.getGoods().stream().map(this::buildCreateRawMaterialPo).collect(Collectors.toList()));
    }

    public List<CustomerOrderDto> searchCustomerOrder(CustomerOrderDto dto) {
        Map<Long, RawMaterialDto> rawMap = rawMaterialCoreService.searchRawMaterialMaps(null);
        return customerOrdersService.searchAllCustomerOrder().stream().map(po -> buildCustomerOrderDto(po, rawMap)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomerOrder(CustomerOrderDto dto) {

        CustomerOrderPo orderPo = new CustomerOrderPo();
        orderPo.setId(dto.getId());
        orderPo.setState(CustomerOrderStatusEnum.CANCEL.getDesc());
        CustomerOrderPo searchPo = customerOrdersService.searchCustomerOrder(orderPo.getId());
        if (!searchPo.getState().equals(CustomerOrderStatusEnum.NOT_CONFIRM.getDesc())) {
            return;
        }
        List<CustomerGoodPo> searchPoList = customerOrdersService.searchCustomerGoods(orderPo.getId());

        customerOrdersService.updateState(orderPo);
        //必须放最后面
        rawMaterialCoreService.updateNumber(searchPoList.stream().map(this::buildDeleteRawMaterialPo).collect(Collectors.toList()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerOrder(CustomerOrderDto dto) {
        CustomerOrderPo orderPo = new CustomerOrderPo();
        orderPo.setId(dto.getId());
        orderPo.setFlorist(dto.getFlorist());
        customerOrdersService.updateFlorist(orderPo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirmCustomerOrder(CustomerOrderDto dto) {
        CustomerOrderPo orderPo = new CustomerOrderPo();
        orderPo.setId(dto.getId());
        orderPo.setState(CustomerOrderStatusEnum.CONFIRM.getDesc());
        CustomerOrderPo searchPo = customerOrdersService.searchCustomerOrder(orderPo.getId());
        if (!searchPo.getState().equals(CustomerOrderStatusEnum.NOT_CONFIRM.getDesc())) {
            return;
        }
        List<CustomerGoodPo> searchPoList = customerOrdersService.searchCustomerGoods(orderPo.getId());

        customerOrdersService.updateState(orderPo);
        //必须放最后面
        rawMaterialCoreService.updateNumber(searchPoList.stream().map(this::buildConfirmRawMaterialPo).collect(Collectors.toList()));
    }

    private CustomerOrderDto buildCustomerOrderDto(CustomerOrderPo po, Map<Long, RawMaterialDto> rawMap) {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        customerOrderDto.setId(po.getId());
        customerOrderDto.setExternalOrderId(po.getExternalOrderId());
        customerOrderDto.setOrderType(po.getOrderType());
        customerOrderDto.setCustomerSource(po.getCustomerSource());
        customerOrderDto.setCustomerName(po.getCustomerName());
        customerOrderDto.setCustomerTel(po.getCustomerTel());
        customerOrderDto.setAmount(po.getAmount());
        customerOrderDto.setFlorist(po.getFlorist());
        customerOrderDto.setRemarks(po.getRemarks());
        customerOrderDto.setState(po.getState());
        customerOrderDto.setFreight(po.getFreight());
        customerOrderDto.setDeliveryAddress(po.getDeliveryAddress());
        customerOrderDto.setDeliveryAt(po.getDeliveryAt());
        customerOrderDto.setOrderCreatedAt(po.getOrderCreatedAt());
        customerOrderDto.setCreatedAt(po.getCreatedAt());
        customerOrderDto.setGoods(searchCustomerGoods(po.getId(), rawMap));
        return customerOrderDto;
    }

    private List<CustomerGoodDto> searchCustomerGoods(Long id, Map<Long, RawMaterialDto> rawMap) {
        return customerOrdersService.searchCustomerGoods(id).stream().map(good -> buildCustomerGoodDto(good, rawMap)).collect(Collectors.toList());
    }

    private CustomerGoodDto buildCustomerGoodDto(CustomerGoodPo good, Map<Long, RawMaterialDto> rawMap) {
        CustomerGoodDto customerGoodDto = new CustomerGoodDto();
        RawMaterialDto rawMaterialDto = rawMap.get(good.getMaterialId());
        customerGoodDto.setId(good.getId());
        customerGoodDto.setOrderId(good.getOrderId());
        customerGoodDto.setGoodId(good.getMaterialId());
        customerGoodDto.setNumber(good.getNumber());
        customerGoodDto.setPrice(good.getPrice());
        if (rawMaterialDto != null) {
            customerGoodDto.setName(rawMaterialDto.getName());
            customerGoodDto.setLevel(rawMaterialDto.getLevel());
            customerGoodDto.setClassify(rawMaterialDto.getClassify());
            customerGoodDto.setColour(rawMaterialDto.getColour());
        }
        return customerGoodDto;
    }

    private RawMaterialDto buildCreateRawMaterialPo(CustomerGoodDto customerGoodDto) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setId(customerGoodDto.getGoodId());
        rawMaterialDto.setPresellNumberChange(customerGoodDto.getNumber());
        rawMaterialDto.setRealNumberChange(-customerGoodDto.getNumber());
        return rawMaterialDto;
    }

    private RawMaterialDto buildDeleteRawMaterialPo(CustomerGoodPo customerGoodPo) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setId(customerGoodPo.getMaterialId());
        rawMaterialDto.setPresellNumberChange(-customerGoodPo.getNumber());
        rawMaterialDto.setRealNumberChange(customerGoodPo.getNumber());
        return rawMaterialDto;
    }

    private RawMaterialDto buildConfirmRawMaterialPo(CustomerGoodPo customerGoodPo) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setId(customerGoodPo.getMaterialId());
        rawMaterialDto.setNumberChange(-customerGoodPo.getNumber());
        rawMaterialDto.setPresellNumberChange(-customerGoodPo.getNumber());
        rawMaterialDto.setSalesNumberChange(customerGoodPo.getNumber());
        return rawMaterialDto;
    }

    private CustomerGoodPo buildCustomerGood(CustomerGoodDto goodDto, CustomerOrderPo orderPo) {
        CustomerGoodPo purchaseGoodPo = new CustomerGoodPo();
        purchaseGoodPo.setId(goodDto.getId());
        purchaseGoodPo.setOrderId(orderPo.getId());
        purchaseGoodPo.setMaterialId(goodDto.getGoodId());
        purchaseGoodPo.setNumber(goodDto.getNumber());
        purchaseGoodPo.setPrice(goodDto.getPrice());
        return purchaseGoodPo;
    }

    private CustomerOrderPo buildCustomerOrderPo(CustomerOrderDto dto) {
        CustomerOrderPo customerOrderPo = new CustomerOrderPo();
        customerOrderPo.setId(dto.getId());
        customerOrderPo.setExternalOrderId(dto.getExternalOrderId());
        customerOrderPo.setOrderType(dto.getOrderType());
        customerOrderPo.setCustomerSource(dto.getCustomerSource());
        customerOrderPo.setCustomerName(dto.getCustomerName());
        customerOrderPo.setCustomerTel(dto.getCustomerTel());
        customerOrderPo.setAmount(dto.getAmount());
        customerOrderPo.setFlorist(dto.getFlorist());
        customerOrderPo.setRemarks(dto.getRemarks());
        customerOrderPo.setState(dto.getState());
        customerOrderPo.setFreight(dto.getFreight() == null ? 0f : dto.getFreight());
        customerOrderPo.setDeliveryAddress(dto.getDeliveryAddress());
        customerOrderPo.setDeliveryAt(dto.getDeliveryAt());
        customerOrderPo.setOrderCreatedAt(dto.getOrderCreatedAt());
        customerOrderPo.setCreatedAt(dto.getCreatedAt());
        return customerOrderPo;
    }

}
