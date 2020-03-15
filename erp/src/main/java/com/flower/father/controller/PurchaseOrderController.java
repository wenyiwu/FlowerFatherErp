package com.flower.father.controller;

import com.flower.father.model.dto.PurchaseGoodDto;
import com.flower.father.model.dto.PurchaseOrderDto;
import com.flower.father.model.param.PageParam;
import com.flower.father.model.param.PurchaseGoodParam;
import com.flower.father.model.param.PurchaseOrderParam;
import com.flower.father.model.response.LoginResponse;
import com.flower.father.model.response.PurchaseGoodResponse;
import com.flower.father.model.response.PurchaseOrderResponse;
import com.flower.father.service.PurchaseOrdersCoreService;
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

//
//		adminRouter.POST("/order/purchase", api.CreatePurchaseOrder)
//                adminRouter.GET("/orders/purchase", api.ListPurchaseOrders)
//                adminRouter.DELETE("/order/purchase/:id", api.DeletePurchaseOrder)
//                adminRouter.PATCH("/order/purchase/:id/confirm", api.ConfirmPurchaseOrder)

/**
 * @author eiven
 */
@CrossOrigin
@Controller
@RequestMapping("/api/v1")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrdersCoreService ordersCoreService;

    @PostMapping("/order/purchase")
    @ResponseBody
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(@RequestBody PurchaseOrderParam param){
        if(CollectionUtils.isEmpty(param.getGoods())) {
            return new ResponseEntity("没有商品", HttpStatus.BAD_REQUEST);
        }
        PurchaseOrderDto purchaseOrderDto = null;
        try {
            purchaseOrderDto = buildPurchaseOrderDto(param);
        } catch (ParseException e) {
//            e.printStackTrace();
            return new ResponseEntity("日期格式错误", HttpStatus.BAD_REQUEST);
        }
        ordersCoreService.createPurchaseOrder(purchaseOrderDto);
        return new ResponseEntity<>(buildPurchaseOrderResponse(purchaseOrderDto), HttpStatus.OK);
    }

    @GetMapping("/orders/purchase")
    @ResponseBody
    public ResponseEntity<List<PurchaseOrderResponse>> listPurchaseOrders(PageParam param){
        return new ResponseEntity<>(ordersCoreService.searchPurchaseOrder(null).stream().map(this::buildPurchaseOrderResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    private PurchaseOrderDto buildPurchaseOrderDto(PurchaseOrderParam param) throws ParseException {
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
        purchaseOrderDto.setProvider(param.getProvider());
        purchaseOrderDto.setRemarks(param.getRemarks());
        purchaseOrderDto.setAmount(param.getAmount());
        purchaseOrderDto.setFreight(param.getFreight());
        purchaseOrderDto.setOrderCreatedAt(TimeUtils.stringToDate(param.getCreatedAt()));
        purchaseOrderDto.setGoods(param.getGoods().stream().map(this::buildPurchaseGoodDto).collect(Collectors.toList()));
        return purchaseOrderDto;
    }

    private PurchaseGoodDto buildPurchaseGoodDto(PurchaseGoodParam purchaseGoodParam) {
        PurchaseGoodDto purchaseGoodDto = new PurchaseGoodDto();
        purchaseGoodDto.setGoodId(purchaseGoodParam.getId());
        purchaseGoodDto.setNumber(purchaseGoodParam.getNumber());
        purchaseGoodDto.setPrice(purchaseGoodParam.getPrice());
        return purchaseGoodDto;
    }

    private PurchaseOrderResponse buildPurchaseOrderResponse(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
        purchaseOrderResponse.setId(purchaseOrderDto.getId());
        purchaseOrderResponse.setProvider(purchaseOrderDto.getProvider());
        purchaseOrderResponse.setRemarks(purchaseOrderDto.getRemarks());
        purchaseOrderResponse.setAmount(purchaseOrderDto.getAmount());
        purchaseOrderResponse.setFreight(purchaseOrderDto.getFreight());
        purchaseOrderResponse.setOrderCreatedAt(TimeUtils.dateToString(purchaseOrderDto.getOrderCreatedAt()));
        purchaseOrderResponse.setCreatedAt(TimeUtils.dateToString(purchaseOrderDto.getCreatedAt()));
        purchaseOrderResponse.setGoods(purchaseOrderDto.getGoods().stream().map(this::buildPurchaseGoodResponse).collect(Collectors.toList()));
        return purchaseOrderResponse;
    }

    private PurchaseGoodResponse buildPurchaseGoodResponse(PurchaseGoodDto purchaseOrderDto) {
        PurchaseGoodResponse purchaseGoodResponse = new PurchaseGoodResponse();
        purchaseGoodResponse.setGoodId(purchaseOrderDto.getGoodId());
        purchaseGoodResponse.setName(purchaseOrderDto.getName());
        purchaseGoodResponse.setLevel(purchaseOrderDto.getLevel());
        purchaseGoodResponse.setClassify(purchaseOrderDto.getClassify());
        purchaseGoodResponse.setColour(purchaseOrderDto.getColour());
        purchaseGoodResponse.setNumber(purchaseOrderDto.getNumber());
        purchaseGoodResponse.setPrice(purchaseOrderDto.getPrice());
        return purchaseGoodResponse;
    }

    /**
     * 删除后，库存重新计算
     * @param id
     * @return
     */
    @RequestMapping("/order/purchase/{id}")
    @ResponseBody
    public ResponseEntity<LoginResponse> deletePurchaseOrder(@PathVariable(name = "id") String id){
        LoginResponse response = new LoginResponse();
        response.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInVzZXJfaWQiOiJicGthMjIxOTRjZzQwZ2I0dnZyMCIsInBlcl9hZGRyIjoiMTI3LjAuMC4xIiwicm9sZXMiOlsiYWRtaW4iXSwiZXhwIjoxNTg0MjgzOTQyLCJpYXQiOjE1ODQwMjQ3NDIsImlzcyI6Im1vZ3V0b3UifQ.p7iWXhSZLZYYT-4SpwUuvDs8hP0O5pfTf-0DBlf6iyQ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
