package cn.edu.tju.mapper;

import cn.edu.tju.entity.Role;
import cn.edu.tju.entity.RoleAndPer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRolesByPId(Integer id);

    List<Role> selectRolesByUserId(Integer id);

    List<RoleAndPer> selectWithPers();
}
