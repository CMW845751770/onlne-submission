package cn.edu.tju.service.impl;

import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.RoleAndPer;
import cn.edu.tju.entity.RolePermission;
import cn.edu.tju.mapper.RoleMapper;
import cn.edu.tju.mapper.RolePermissionMapper;
import cn.edu.tju.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    @Cacheable(key = "#root.methodName+'['+#id+']'")
    public List<Role> selectRolesByPId(Integer id) {
        return roleMapper.selectRolesByPId(id);
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#id+']'")
    public List<Role> selectRolesByUserId(Integer id) {
        return roleMapper.selectRolesByUserId(id);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<RoleAndPer> selectWithPers() {
        return roleMapper.selectWithPers();
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean saveRole(Role role, Integer[] pids) {
        roleMapper.insert(role);
        //int a= 1/0;
        insertRolePer(role, pids);
        return true;
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean updateRole(Role role, Integer[] pids) {
        role.setGmtModified(LocalDateTime.now());
        roleMapper.updateById(role);
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", role.getId());
        rolePermissionMapper.delete(wrapper);//先清空角色的权限信息，再重新保存一份
        insertRolePer(role, pids);
        return true;
    }

    private void insertRolePer(Role role, Integer[] pids) {
        RolePermission rp;
        for (Integer pid : pids) {
            rp = new RolePermission();
            rp.setRid(role.getId());
            rp.setPid(pid);
            rp.setGmtCreate(LocalDateTime.now());
            rp.setGmtModified(LocalDateTime.now());
            rolePermissionMapper.insert(rp);
        }
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean updateById(Role entity) {
        return super.updateById(entity);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<Role> list() {
        return list(Wrappers.emptyWrapper());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#id+']'")
    public Role getById(Serializable id) {
        return super.getById(id);
    }
}
