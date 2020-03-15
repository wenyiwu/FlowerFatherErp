package com.flower.father.service;

import com.flower.father.mapper.service.PurchaseOrdersService;
import com.flower.father.model.dto.PurchaseGoodDto;
import com.flower.father.model.dto.PurchaseOrderDto;
import com.flower.father.model.dto.RawMaterialDto;
import com.flower.father.model.po.PurchaseGoodPo;
import com.flower.father.model.po.PurchaseOrderPo;
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
public class PurchaseOrdersCoreService {

    @Autowired
    private PurchaseOrdersService purchaseOrdersService;

    @Autowired
    private RawMaterialCoreService rawMaterialCoreService;

    @Transactional(rollbackFor = Exception.class)
    public void createPurchaseOrder(PurchaseOrderDto dto){
        PurchaseOrderPo orderPo = buildPurchaseOrderPo(dto);
        purchaseOrdersService.insertPurchaseOrder(orderPo);
        dto.setId(orderPo.getId());
        for(PurchaseGoodDto goodDto : dto.getGoods()) {
            PurchaseGoodPo goodPo = buildPurchaseGood(goodDto, orderPo);
            purchaseOrdersService.insertPurchaseGood(goodPo);
        }

        //必须放最后面
        rawMaterialCoreService.updateNumber(dto.getGoods().stream().map(this::buildRawMaterialPo).collect(Collectors.toList()));
    }

    public List<PurchaseOrderDto> searchPurchaseOrder(PurchaseOrderDto dto){
        Map<Long, RawMaterialDto> rawMap = rawMaterialCoreService.searchRawMaterialMaps(null);
        return purchaseOrdersService.searchAllPurchaseOrder().stream().map(po -> buildPurchaseOrderDto(po, rawMap)).collect(Collectors.toList());
    }

    private PurchaseOrderDto buildPurchaseOrderDto(PurchaseOrderPo po, Map<Long, RawMaterialDto> rawMap) {
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
        purchaseOrderDto.setId(po.getId());
        purchaseOrderDto.setProvider(po.getProvider());
        purchaseOrderDto.setRemarks(po.getRemarks());
        purchaseOrderDto.setAmount(po.getAmount());
        purchaseOrderDto.setFreight(po.getFreight());
        purchaseOrderDto.setOrderCreatedAt(po.getOrderCreatedAt());
        purchaseOrderDto.setCreatedAt(po.getCreatedAt());
        purchaseOrderDto.setGoods(searchPurchaseGoods(po.getId(), rawMap));
        return purchaseOrderDto;
    }

    private List<PurchaseGoodDto> searchPurchaseGoods(Long id, Map<Long, RawMaterialDto> rawMap) {
        return purchaseOrdersService.searchPurchaseGoods(id).stream().map(good -> buildPurchaseGoodDto(good, rawMap)).collect(Collectors.toList());
    }

    private PurchaseGoodDto buildPurchaseGoodDto(PurchaseGoodPo good, Map<Long, RawMaterialDto> rawMap) {
        PurchaseGoodDto purchaseGoodDto = new PurchaseGoodDto();
        RawMaterialDto rawMaterialDto = rawMap.get(good.getMaterialId());
        purchaseGoodDto.setId(good.getId());
        purchaseGoodDto.setOrderId(good.getOrderId());
        purchaseGoodDto.setGoodId(good.getMaterialId());
        purchaseGoodDto.setNumber(good.getNumber());
        purchaseGoodDto.setPrice(good.getPrice());
        if(rawMaterialDto != null) {
            purchaseGoodDto.setName(rawMaterialDto.getName());
            purchaseGoodDto.setLevel(rawMaterialDto.getLevel());
            purchaseGoodDto.setClassify(rawMaterialDto.getClassify());
            purchaseGoodDto.setColour(rawMaterialDto.getColour());
        }
        return purchaseGoodDto;
    }

    private RawMaterialDto buildRawMaterialPo(PurchaseGoodDto purchaseGoodDto) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setId(purchaseGoodDto.getGoodId());
        rawMaterialDto.setNumberChange(purchaseGoodDto.getNumber());
        rawMaterialDto.setRealNumberChange(purchaseGoodDto.getNumber());
        return rawMaterialDto;
    }

    private PurchaseGoodPo buildPurchaseGood(PurchaseGoodDto goodDto, PurchaseOrderPo orderPo) {
        PurchaseGoodPo purchaseGoodPo = new PurchaseGoodPo();
        purchaseGoodPo.setOrderId(orderPo.getId());
        purchaseGoodPo.setMaterialId(goodDto.getGoodId());
        purchaseGoodPo.setNumber(goodDto.getNumber());
        purchaseGoodPo.setPrice(goodDto.getPrice());
        return purchaseGoodPo;
    }

    private PurchaseOrderPo buildPurchaseOrderPo(PurchaseOrderDto dto) {
        PurchaseOrderPo purchaseOrderPo = new PurchaseOrderPo();
        purchaseOrderPo.setId(dto.getId());
        purchaseOrderPo.setAmount(dto.getAmount());
        purchaseOrderPo.setFreight(dto.getFreight());
        purchaseOrderPo.setProvider(dto.getProvider());
        purchaseOrderPo.setRemarks(dto.getRemarks());
        purchaseOrderPo.setState("有效");
        purchaseOrderPo.setOrderCreatedAt(dto.getOrderCreatedAt());
        return purchaseOrderPo;
    }
}
