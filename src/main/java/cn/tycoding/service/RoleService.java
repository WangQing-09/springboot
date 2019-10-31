package cn.tycoding.service;

import cn.tycoding.entity.Role;

public interface RoleService extends BaseService<Role> {
    Role findByName(String name);
}
