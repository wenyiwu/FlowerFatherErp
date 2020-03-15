package com.flower.father.controller;

import com.flower.father.model.dto.RawMaterialDto;
import com.flower.father.model.param.RawMaterialParam;
import com.flower.father.model.response.RawMaterialResponse;
import com.flower.father.service.RawMaterialCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eiven
 */
@CrossOrigin
@Controller
@RequestMapping("/api/v1")
public class RawMaterialController {

    @Autowired
    private RawMaterialCoreService rawMaterialCoreService;

    @PostMapping("/commodity")
    @ResponseBody
    public ResponseEntity<RawMaterialResponse> createRawMaterial(@RequestBody RawMaterialParam param){
        RawMaterialDto dto = buildRawMaterialDto(param);
        rawMaterialCoreService.createRawMaterial(dto);
        return new ResponseEntity<>(buildRawMaterialResponse(dto), HttpStatus.OK);
    }

    @RequestMapping("/admin/commodities")
    @ResponseBody
    public ResponseEntity<List<RawMaterialResponse>> searchRawMaterials(){
        List<RawMaterialDto> dtos = rawMaterialCoreService.searchRawMaterials(null);
        return new ResponseEntity<>(dtos.stream().map(this::buildRawMaterialResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping("/commodities")
    @ResponseBody
    public ResponseEntity<List<RawMaterialResponse>> searchInventories(){
        List<RawMaterialDto> dtos = rawMaterialCoreService.searchRawMaterials(null);
        return new ResponseEntity<>(dtos.stream().map(this::buildRawMaterialResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    private RawMaterialResponse buildRawMaterialResponse(RawMaterialDto rawMaterial) {
        RawMaterialResponse rawMaterialResponse = new RawMaterialResponse();
        rawMaterialResponse.setId(rawMaterial.getId());
        rawMaterialResponse.setName(rawMaterial.getName());
        rawMaterialResponse.setLevel(rawMaterial.getLevel());
        rawMaterialResponse.setClassify(rawMaterial.getClassify());
        rawMaterialResponse.setColour(rawMaterial.getColour());
        rawMaterialResponse.setNumber(rawMaterial.getNumber());
        rawMaterialResponse.setPresellNumber(rawMaterial.getPresellNumber());
        rawMaterialResponse.setRealNumber(rawMaterial.getRealNumber());
        rawMaterialResponse.setSalesNumber(rawMaterial.getSalesNumber());
        return rawMaterialResponse;
    }

    private RawMaterialDto buildRawMaterialDto(RawMaterialParam param) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setName(param.getName());
        rawMaterialDto.setLevel(param.getLevel());
        rawMaterialDto.setClassify(param.getClassify());
        rawMaterialDto.setColour(param.getColour());
        return rawMaterialDto;
    }
}
