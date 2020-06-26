package cn.edu.tju.service;

import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.RoleAndPer;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface RoleService extends IService<Role> {

    List<Role> selectRolesByPId(Integer id);

    List<Role> selectRolesByUserId(Integer id);

    List<RoleAndPer> selectWithPers();

    boolean saveRole(Role role, Integer[] pids);

    boolean updateRole(Role role, Integer[] pids);

    @Override
    boolean updateById(Role entity);

    @Override
    boolean removeById(Serializable id);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);

    @Override
    boolean update(Role entity, Wrapper<Role> updateWrapper);

    List<Role> list();

    @Override
    Role getById(Serializable id);
}
