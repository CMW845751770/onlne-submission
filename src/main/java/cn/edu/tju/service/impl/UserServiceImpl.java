package cn.edu.tju.service.impl;

import cn.edu.tju.entity.User;
import cn.edu.tju.entity.UserAndRole;
import cn.edu.tju.entity.UserRole;
import cn.edu.tju.mapper.UserMapper;
import cn.edu.tju.mapper.UserRoleMapper;
import cn.edu.tju.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails;
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "user", allEntries = true)
            }
    )
    public boolean saveUser(User user, Integer[] rids) {
        userMapper.insert(user);
        insertUserRole(user, rids);
        return true;
    }

    private void insertUserRole(User user, Integer[] rids) {
        UserRole ur;
        for (Integer rid : rids) {
            ur = new UserRole();
            ur.setUid(user.getId());
            ur.setRid(rid);
            ur.setGmtCreate(LocalDateTime.now());
            ur.setGmtModified(LocalDateTime.now());
            userRoleMapper.insert(ur);
        }
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "user", allEntries = true)
            }
    )
    public boolean updateUser(User user, Integer[] rids) {
        user.setGmtModified(LocalDateTime.now());
        userMapper.updateById(user);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", user.getId());
        userRoleMapper.delete(wrapper);//先清空角色的权限信息，再重新保存一份
        insertUserRole(user, rids);
        return true;
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<UserAndRole> selectWithRoles() {
        return userMapper.selectWithRoles();
    }
}
