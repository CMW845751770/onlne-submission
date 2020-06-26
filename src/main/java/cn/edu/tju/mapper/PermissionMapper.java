package cn.edu.tju.mapper;

import cn.edu.tju.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findByUserId(Integer id);

    List<Permission> selectByUrl(String url);

    List<Permission> findByUsername(String username);

    List<Permission> findByAuths(List auths);
}
