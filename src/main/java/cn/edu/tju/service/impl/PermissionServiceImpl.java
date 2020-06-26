package cn.edu.tju.service.impl;

import cn.edu.tju.entity.Permission;
import cn.edu.tju.entity.Sub;
import cn.edu.tju.mapper.PermissionMapper;
import cn.edu.tju.mapper.SubMapper;
import cn.edu.tju.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
@CacheConfig(cacheNames = "permission")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private SubMapper subMapper;

    @Override
    @Cacheable(key = "#root.methodName+'['+#id+']'")
    public List<Permission> findByUserId(Integer id) {
        return permissionMapper.findByUserId(id);
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#url+']'")
    public List<Permission> selectByUrl(String url) {
        return permissionMapper.selectByUrl(url);
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#username+']'")
    public List<Permission> findByUsername(String username) {
        return permissionMapper.findByUsername(username);
    }

    @Override
    public List<Permission> findByAuths(Collection<? extends GrantedAuthority> authorities) {
        ArrayList<String> array = new ArrayList<>();
        String s = authorities.toString();
        String[] strings = s.substring(1, s.length() - 1).split(",");
        for (String string : strings) {
            array.add(string.trim());
        }
        List<Permission> auths = permissionMapper.findByAuths(array);
        for (Permission auth : auths) {
            //添加消息次数
            if("在线审稿".equals(auth.getName())){
                QueryWrapper<Sub> wrapper = new QueryWrapper<>();
                wrapper.in("status",1,2);
                Integer count = subMapper.selectCount(wrapper);
                auth.setCount(count);
                break;
            }
        }
        return auths;
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
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean update(Permission entity, Wrapper<Permission> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean updateById(Permission entity) {
        return super.updateById(entity);
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<Permission> list() {
        return list(Wrappers.emptyWrapper());
    }

    @Override
    @Cacheable(key = "#root.methodName+'['+#id+']'")
    public Permission getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "role", allEntries = true),
                    @CacheEvict(cacheNames = "permission", allEntries = true)
            }
    )
    public boolean save(Permission entity) {
        return super.save(entity);
    }
}