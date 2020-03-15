package com.flower.father.mapper;


import com.flower.father.model.po.UserPo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "userId",  column = "user_id"),
            @Result(property = "nickName", column = "nick_name")
    })
    List<UserPo> getAll();
}
