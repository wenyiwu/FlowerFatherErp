package com.flower.father.controller;

import com.flower.father.enums.CustomerOrderStatusEnum;
import com.flower.father.model.dto.CustomerGoodDto;
import com.flower.father.model.dto.CustomerOrderDto;
import com.flower.father.model.param.CustomerGoodParam;
import com.flower.father.model.param.CustomerOrderParam;
import com.flower.father.model.param.PageParam;
import com.flower.father.model.response.CustomerGoodResponse;
import com.flower.father.model.response.CustomerOrderResponse;
import com.flower.father.service.CustomerOrdersCoreService;
import com.flower.father.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/api/v1")
public class CustomerOrderController {

    @Autowired
    private CustomerOrdersCoreService customerOrdersCoreService;

    @PostMapping("/order/custormer")
    @ResponseBody
    public ResponseEntity<CustomerOrderResponse> createCustomerOrder(@RequestBody CustomerOrderParam param){
        if(CollectionUtils.isEmpty(param.getGoods())) {
            return new ResponseEntity("没有商品", HttpStatus.BAD_REQUEST);
        }
        CustomerOrderDto customerOrderDto = null;
        try {
            customerOrderDto = buildCustomerOrderDto(param);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity("日期格式错误", HttpStatus.BAD_REQUEST);
        }
        customerOrdersCoreService.createCustomerOrder(customerOrderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders/custormer")
    @ResponseBody
    public ResponseEntity<List<CustomerOrderResponse>> searchCustomerOrder(PageParam param){
        return new ResponseEntity<>(customerOrdersCoreService.searchCustomerOrder(null).stream().map(this::buildCustomerOrderResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/order/custormer")
    @ResponseBody
    public ResponseEntity<CustomerOrderResponse> deleteCustomerOrder(@RequestBody CustomerOrderParam param){
        try {
            customerOrdersCoreService.deleteCustomerOrder(buildCustomerOrderDto(param));
        } catch (ParseException e) {
            return new ResponseEntity("日期格式错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order/custormer/confirm")
    @ResponseBody
    public ResponseEntity<CustomerOrderResponse> confirmCustomerOrder(@RequestBody CustomerOrderParam param){
        try {
            customerOrdersCoreService.confirmCustomerOrder(buildCustomerOrderDto(param));
        } catch (ParseException e) {
            return new ResponseEntity("日期格式错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order/custormer/update")
    @ResponseBody
    public ResponseEntity<List<CustomerOrderResponse>> updateCustomerOrder(@RequestBody CustomerOrderParam param){
        try {
            customerOrdersCoreService.updateCustomerOrder(buildCustomerOrderDto(param));
        } catch (ParseException e) {
            return new ResponseEntity("日期格式错误", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private CustomerOrderResponse buildCustomerOrderResponse(CustomerOrderDto customerOrderDto) {
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        customerOrderResponse.setId(customerOrderDto.getId());
        customerOrderResponse.setExternalOrderId(customerOrderDto.getExternalOrderId());
        customerOrderResponse.setOrderType(customerOrderDto.getOrderType());
        customerOrderResponse.setCustomerSource(customerOrderDto.getCustomerSource());
        customerOrderResponse.setCustomerName(customerOrderDto.getCustomerName());
        customerOrderResponse.setCustomerTel(customerOrderDto.getCustomerTel());
        customerOrderResponse.setOrderCreatedAt(TimeUtils.dateToString(customerOrderDto.getOrderCreatedAt()));
        customerOrderResponse.setDeliveryAddress(customerOrderDto.getDeliveryAddress());
        customerOrderResponse.setDeliveryAt(TimeUtils.dateToString(customerOrderDto.getDeliveryAt()));
        customerOrderResponse.setAmount(customerOrderDto.getAmount());
        customerOrderResponse.setFlorist(customerOrderDto.getFlorist());
        customerOrderResponse.setRemarks(customerOrderDto.getRemarks());
        customerOrderResponse.setState(customerOrderDto.getState());
        customerOrderResponse.setFreight(customerOrderDto.getFreight());
        customerOrderResponse.setGoods(customerOrderDto.getGoods().stream().map(this::buildCustomerGoodResponse).collect(Collectors.toList()));
        return customerOrderResponse;
    }

    private CustomerGoodResponse buildCustomerGoodResponse(CustomerGoodDto customerGoodDto) {
        CustomerGoodResponse customerGoodResponse = new CustomerGoodResponse();
        customerGoodResponse.setGoodId(customerGoodDto.getGoodId());
        customerGoodResponse.setName(customerGoodDto.getName());
        customerGoodResponse.setLevel(customerGoodDto.getLevel());
        customerGoodResponse.setClassify(customerGoodDto.getClassify());
        customerGoodResponse.setColour(customerGoodDto.getColour());
        customerGoodResponse.setNumber(customerGoodDto.getNumber());
        customerGoodResponse.setPrice(customerGoodDto.getPrice());
        return customerGoodResponse;
    }

    private CustomerOrderDto buildCustomerOrderDto(CustomerOrderParam param) throws ParseException {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        customerOrderDto.setId(param.getId());
        customerOrderDto.setExternalOrderId(param.getExternalOrderId());
        customerOrderDto.setOrderType(param.getOrderType());
        customerOrderDto.setCustomerSource(param.getCustomerSource());
        customerOrderDto.setCustomerName(param.getCustomerName());
        customerOrderDto.setCustomerTel(param.getCustomerTel());
        customerOrderDto.setAmount(param.getAmount());
        customerOrderDto.setFlorist(param.getFlorist());
        customerOrderDto.setRemarks(param.getRemarks());
        customerOrderDto.setState(CustomerOrderStatusEnum.NOT_CONFIRM.getDesc());
        customerOrderDto.setFreight(param.getFreight());
        customerOrderDto.setDeliveryAddress(param.getDeliveryAddress());
        customerOrderDto.setDeliveryAt(TimeUtils.stringToDate(param.getDeliveryAt()));
        customerOrderDto.setOrderCreatedAt(TimeUtils.stringToDate(param.getOrderCreatedAt()));
        if(!CollectionUtils.isEmpty(param.getGoods())) {
            customerOrderDto.setGoods(param.getGoods().stream().map(this::buildCustomerGoodDto).collect(Collectors.toList()));
        }
        return customerOrderDto;
    }

    private CustomerGoodDto buildCustomerGoodDto(CustomerGoodParam customerGoodParam) {
        CustomerGoodDto customerGoodDto = new CustomerGoodDto();
        customerGoodDto.setGoodId(customerGoodParam.getId());
        customerGoodDto.setNumber(customerGoodParam.getNumber());
        customerGoodDto.setPrice(customerGoodParam.getPrice());
        return customerGoodDto;
    }
}
