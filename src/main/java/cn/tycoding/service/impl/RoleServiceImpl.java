package cn.tycoding.service.impl;

import cn.tycoding.entity.Role;
import cn.tycoding.mapper.RoleMapper;
import cn.tycoding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangqing
 * @date 2019-10-22
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findByName(String name) {
        return roleMapper.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<Role> findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public void create(Role role) {
         roleMapper.create(role);
    }

    @Override
    public void delete(Long... ids) {
        for (Long id : ids) {
            roleMapper.delete(id);
        }

    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);

    }
}
