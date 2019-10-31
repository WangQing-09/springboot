package cn.tycoding.mapper;

import cn.tycoding.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> findAll();

    List<Role> findById(Long id);

    List<String> selectPermissionByUserId(@Param("id") Integer id);

    void create(Role role);

    void delete(Long id);

    void update(Role role);

    Role findByName(String name);
}
