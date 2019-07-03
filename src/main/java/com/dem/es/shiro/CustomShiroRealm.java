package com.dem.es.shiro;

import com.dem.es.entity.dto.LoginUserDTO;
import com.dem.es.entity.po.SysPermission;
import com.dem.es.entity.po.SysRole;
import com.dem.es.entity.po.SysUser;
import com.dem.es.service.JedisClient;
import com.dem.es.service.SysPermissionService;
import com.dem.es.service.SysRoleService;
import com.dem.es.service.SysUserService;
import com.dem.es.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 */
@Slf4j
public class CustomShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private SysRoleService sysRoleService;

    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "es:shiro_login_count:";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "es:shiro_is_lock:";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。

        List<SysRole> roleList = sysRoleService.selectByUser(userId);
        Set<String> roleSet = new HashSet<>();
        for (SysRole role : roleList) {
            roleSet.add(role.getType());
        }

        //根据用户ID查询权限（permission），放入到Authorization里。
        List<SysPermission> permissionList = sysPermissionService.selectByRoles(roleSet);
        Set<String> permissionSet = new HashSet<String>();
        for (SysPermission Permission : permissionList) {
            permissionSet.add(Permission.getName());
        }
        info.setStringPermissions(permissionSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //访问一次，计数一次
        jedisClient.incr(SHIRO_LOGIN_COUNT+name);
        //计数大于5时，设置用户被锁定一小时
        if(Integer.valueOf(jedisClient.get(SHIRO_LOGIN_COUNT+name))>=5){
            jedisClient.set(SHIRO_IS_LOCK+name, "LOCK");
            jedisClient.expire(SHIRO_IS_LOCK+name, 1*3600);
        }
        if ("LOCK".equals(jedisClient.get(SHIRO_IS_LOCK + name))) {
            throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
        }

        SysUser user = null;
        // 从数据库获取对应用户名密码的用户
        user = sysUserService.getByUserName(name);
        if (null == user || !user.getPassword().equals(MD5Util.encode(password + user.getSalt()))) {
            throw new AccountException("帐号或密码不正确！");
        } else if (SysUser.UserState.DISABLE.getCode().equals(user.getStatus())) {
            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
        } else {
            //清空登录计数
            jedisClient.set(SHIRO_LOGIN_COUNT + name, "0");
        }
        LoginUserDTO userInfoDTO = new LoginUserDTO();
        userInfoDTO.setUserid(user.getId());
        userInfoDTO.setUsername(user.getUserName());
        return new SimpleAuthenticationInfo(userInfoDTO, password, getName());
    }
}
