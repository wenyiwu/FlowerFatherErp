package com.flower.father.mapper.service;

import com.flower.father.mapper.RawMaterialMapper;
import com.flower.father.model.po.RawMaterialPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eiven
 */
@Service
public class RawMaterialService {

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    public void insert(RawMaterialPo po) {
        Long id = rawMaterialMapper.insert(po);
        po.setId(id);
    }

    public void update(RawMaterialPo po) {
        rawMaterialMapper.update(po);
    }

    public List<RawMaterialPo> searchList() {
        return rawMaterialMapper.searchList();
    }

    public RawMaterialPo search(Long id) {
        return rawMaterialMapper.search(id);
    }
}
