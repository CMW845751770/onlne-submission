package cn.edu.tju.service;

import cn.edu.tju.entity.Permission;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> findByUserId(Integer id);

    List<Permission> selectByUrl(String url);

    List<Permission> findByUsername(String username);

    List<Permission> findByAuths(Collection<? extends GrantedAuthority> authorities);

    @Override
    boolean updateById(Permission entity);

    @Override
    boolean removeById(Serializable id);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);

    @Override
    boolean update(Permission entity, Wrapper<Permission> updateWrapper);

    List<Permission> list();

    Permission getById(Serializable id);

    @Override
    boolean save(Permission entity);
}
