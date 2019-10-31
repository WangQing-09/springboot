package cn.tycoding.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface PermissionMapper {
    List<String> selectPermissionByUserId(@Param("userId") long userId);
}
