package com.flower.father.service;

import com.flower.father.mapper.service.RawMaterialService;
import com.flower.father.model.dto.RawMaterialDto;
import com.flower.father.model.po.RawMaterialPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author eiven
 */
@Service
public class RawMaterialCoreService {

    @Autowired
    private RawMaterialService rawMaterialService;

    public void createRawMaterial(RawMaterialDto dto){
        rawMaterialService.insert(buildRawMaterialPo(dto));
    }

    public List<RawMaterialDto> searchRawMaterials(RawMaterialDto dto){
        List<RawMaterialPo> pos = rawMaterialService.searchList();
        if(CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }
        return pos.stream().map(this::buildRawMaterialDto).collect(Collectors.toList());
    }

    public Map<Long, RawMaterialDto> searchRawMaterialMaps(RawMaterialDto dto){
        List<RawMaterialDto> pos = searchRawMaterials(dto);
        if(CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_MAP;
        }
        return pos.stream().collect(Collectors.toMap(RawMaterialDto::getId, po -> po));
    }

    public synchronized void updateNumber(List<RawMaterialDto> dtos) {
        for(RawMaterialDto dto : dtos) {
            RawMaterialPo rawMaterialPo = rawMaterialService.search(dto.getId());
            if(rawMaterialPo == null) {
                return;
            }
            if(dto.getNumberChange() != null) {
                rawMaterialPo.setNumber(rawMaterialPo.getNumber() + dto.getNumberChange());
            }
            if(dto.getRealNumberChange() != null) {
                rawMaterialPo.setRealNumber(rawMaterialPo.getRealNumber() + dto.getRealNumberChange());
            }
            if(dto.getPresellNumberChange() != null) {
                rawMaterialPo.setPresellNumber(rawMaterialPo.getPresellNumber() + dto.getPresellNumberChange());
            }
            if(dto.getSalesNumberChange() != null) {
                rawMaterialPo.setSalesNumber(rawMaterialPo.getSalesNumber() + dto.getSalesNumberChange());
            }
            rawMaterialService.update(rawMaterialPo);
        }
    }

    private RawMaterialDto buildRawMaterialDto(RawMaterialPo po) {
        RawMaterialDto rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setId(po.getId());
        rawMaterialDto.setName(po.getName());
        rawMaterialDto.setLevel(po.getLevel());
        rawMaterialDto.setClassify(po.getClassify());
        rawMaterialDto.setColour(po.getColour());
        rawMaterialDto.setNumber(po.getNumber());
        rawMaterialDto.setPresellNumber(po.getPresellNumber());
        rawMaterialDto.setRealNumber(po.getRealNumber());
        rawMaterialDto.setSalesNumber(po.getSalesNumber());
        return rawMaterialDto;
    }

    private RawMaterialPo buildRawMaterialPo(RawMaterialDto dto) {
        RawMaterialPo rawMaterialPo = new RawMaterialPo();
        rawMaterialPo.setId(dto.getId());
        rawMaterialPo.setName(dto.getName());
        rawMaterialPo.setLevel(dto.getLevel());
        rawMaterialPo.setClassify(dto.getClassify());
        rawMaterialPo.setColour(dto.getColour());
        rawMaterialPo.setNumber(dto.getNumber());
        rawMaterialPo.setPresellNumber(dto.getPresellNumber());
        rawMaterialPo.setRealNumber(dto.getRealNumber());
        rawMaterialPo.setSalesNumber(dto.getSalesNumber());
        return rawMaterialPo;
    }
}
