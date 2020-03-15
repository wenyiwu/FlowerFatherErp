package com.flower.father.mapper;

import com.flower.father.model.po.RawMaterialPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RawMaterialMapper {

    @Insert({ "insert into tb_raw_material(name, level, classify, colour) values(#{name}, #{level}, #{classify}, #{colour})" })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(RawMaterialPo po);


    @Update({ "update tb_raw_material set number = #{number}, presell_number = #{presellNumber}, real_number = #{realNumber}, sales_number = #{salesNumber} where id = #{id}" })
    Long update(RawMaterialPo po);

    @Select({ "select * from tb_raw_material" })
    @Results({
            @Result(property = "presellNumber",  column = "presell_number"),
            @Result(property = "realNumber", column = "real_number"),
            @Result(property = "salesNumber", column = "sales_number")
    })
    List<RawMaterialPo> searchList();

    @Select({ "select * from tb_raw_material where id = #{id}" })
    @Results({
            @Result(property = "presellNumber",  column = "presell_number"),
            @Result(property = "realNumber", column = "real_number"),
            @Result(property = "salesNumber", column = "sales_number")
    })
    RawMaterialPo search(Long id);
}
