package com.sppart.admin.sub.user.domain.mapper;

import com.sppart.admin.sub.user.domain.Users;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    Optional<Users> findById(@Param("id") String id);
}
