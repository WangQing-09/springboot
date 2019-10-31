package cn.tycoding.service;

import cn.tycoding.entity.Permission;

import java.util.List;

public interface PermissionService  {
    List<String> selectPermissionByUerId(long userId);
}
