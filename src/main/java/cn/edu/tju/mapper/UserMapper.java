package cn.edu.tju.mapper;

import cn.edu.tju.entity.User;
import cn.edu.tju.entity.UserAndRole;
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
public interface UserMapper extends BaseMapper<User> {

    List<UserAndRole> selectWithRoles();
}
