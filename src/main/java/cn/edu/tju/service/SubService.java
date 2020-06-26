package cn.edu.tju.service;

import cn.edu.tju.entity.Sub;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-27
 */
public interface SubService extends IService<Sub> {

    boolean saveSub(Sub sub, String[] fids);

    boolean updateSub(Sub sub, String[] fids);

    boolean updateById(Sub sub);

    boolean updateById(Sub sub,String msg);
}
