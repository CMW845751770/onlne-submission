package cn.edu.tju.security;

import cn.edu.tju.entity.Permission;
import cn.edu.tju.entity.User;
import cn.edu.tju.service.PermissionService;
import cn.edu.tju.service.RoleService;
import cn.edu.tju.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomDetailService implements UserDetailsService {

    @Autowired
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("username", username);
        User userEntity = userService.getOne(userQueryWrapper);
        if (userEntity != null) {
            //查询用户对应的权限列表
            List<Permission> permissions = permissionService.findByUserId(userEntity.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermCode());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword()
                    ,true,true,true, userEntity.getStatus()==1?true:false
                    , grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("用户: " + username + " 不存在!");
        }
    }
}
