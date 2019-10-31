package cn.tycoding.shiro;

import cn.tycoding.entity.Permission;
import cn.tycoding.entity.Role;
import cn.tycoding.entity.User;
import cn.tycoding.service.PermissionService;
import cn.tycoding.service.RoleService;
import cn.tycoding.service.UserService;
import cn.tycoding.service.impl.PermissionServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangqing
 * @date 2019-10-21
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

      /*  //从凭证中获得用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //根据用户名查询用户对象
        User user = userService.findByName(username);
        //查询用户拥有的角色
        List<Role> list = roleService.findById(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Role role : list) {
            //赋予用户角色
            info.addStringPermission(role.getRole());
        }*/

      User user = (User) principals.getPrimaryPrincipal();
      List<String> permissions = permissionService.selectPermissionByUerId(user.getUserId());

      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      info.addStringPermissions(permissions);

      LOGGER.info("doGetAuthorizationInfo");
      return info;

    }


    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**
         * 1. 从Token中获取输入的用户名密码
         * 2. 通过输入的用户名查询数据库得到密码
         * 3. 调用Authentication进行密码校验
         */

        //获取用户输入的用户名和密码
        //获得当前用户的用户名
        String username = (String) authenticationToken.getPrincipal();
        //密码要用字符数组来接收，因为UsernamePasswordToken(username, password) 存储密码的时候是将字符串类型转化为字符串数组
        String password = new String((char []) authenticationToken.getCredentials());
        //从数据库中根据用户名查找用户
        User user = userService.findByUserName(username);
        //判断用户是否存在，不存在则抛出异常
        if (username == null) {
            //判断用户密码是否匹配，不匹配则抛出异常
            if (!user.getPassword().equals(password)) {
                throw new IncorrectCredentialsException();
            }
            throw new UnknownAccountException("没有在本系统中找到对应的用户信息");
        }

        LOGGER.info("doGetAuthenticationInfo");
        //获取盐值，即用户名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }
}
