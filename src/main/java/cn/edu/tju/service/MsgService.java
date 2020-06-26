package cn.edu.tju.service;

import cn.edu.tju.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-04-07
 */
public interface MsgService extends IService<Msg> {
    boolean save(Msg msg);
}
