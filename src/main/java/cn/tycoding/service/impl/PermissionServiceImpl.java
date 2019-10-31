package cn.tycoding.service.impl;

import cn.tycoding.entity.Permission;
import cn.tycoding.mapper.PermissionMapper;
import cn.tycoding.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangqing
 * @date 2019-10-28
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> selectPermissionByUerId(long userId) {
        return permissionMapper.selectPermissionByUserId(userId);
    }
}
