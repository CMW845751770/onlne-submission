package cn.edu.tju.service.impl;

import cn.edu.tju.entity.Msg;
import cn.edu.tju.mapper.MsgMapper;
import cn.edu.tju.service.MsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-04-07
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {
    @Override
    public boolean save(Msg entity) {
        entity.setIsRead(0);
        entity.setGmtCreate(LocalDateTime.now());
        return super.save(entity);
    }
}
