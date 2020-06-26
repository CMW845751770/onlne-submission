package cn.edu.tju.service;

import cn.edu.tju.entity.User;
import cn.edu.tju.entity.UserAndRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-16
 */
public interface UserService extends IService<User> {
    UserDetails getCurrentUser();

    boolean saveUser(User user, Integer[] rids);

    boolean updateUser(User user, Integer[] rids);

    List<UserAndRole> selectWithRoles();
}
