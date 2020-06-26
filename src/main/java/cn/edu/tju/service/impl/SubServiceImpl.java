package cn.edu.tju.service.impl;

import cn.edu.tju.entity.Msg;
import cn.edu.tju.entity.Sub;
import cn.edu.tju.entity.SubFile;
import cn.edu.tju.mapper.MsgMapper;
import cn.edu.tju.mapper.SubFileMapper;
import cn.edu.tju.mapper.SubMapper;
import cn.edu.tju.service.SubService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-27
 */
@Service
public class SubServiceImpl extends ServiceImpl<SubMapper, Sub> implements SubService {
    @Autowired
    private SubMapper subMapper;
    @Autowired
    private SubFileMapper subFileMapper;
    @Autowired
    private MsgMapper msgMapper;

    @Override
    @Transactional
    public boolean saveSub(Sub sub, String[] fids) {
        sub.setGmtCreate(LocalDateTime.now());
        sub.setGmtModified(LocalDateTime.now());
        sub.setStatus(1);
        subMapper.insert(sub);
        insertSubFile(sub, fids);
        return true;
    }

    @Override
    @Transactional
    public boolean updateSub(Sub sub, String[] fids) {
        subMapper.updateById(sub);
        //先删除原来的
        QueryWrapper<SubFile> wrapper = new QueryWrapper<>();
        wrapper.eq("sid", sub.getId());
        subFileMapper.delete(wrapper);
        insertSubFile(sub, fids);
        return true;
    }

    @Override
    public boolean updateById(Sub entity) {
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean updateById(Sub sub, String msg) {
        if (StringUtils.isNotEmpty(msg)){//保存回复
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            Msg msgEntity = new Msg();
            msgEntity.setUserSender(userDetails.getUsername());
            msgEntity.setGmtCreate(LocalDateTime.now());
            msgEntity.setMsg(msg);
            msgEntity.setType("1");
            msgEntity.setSid(sub.getId());
            msgMapper.insert(msgEntity);
        }
        sub.setGmtModified(LocalDateTime.now());
        return updateById(sub);
    }

    private void insertSubFile(Sub sub, String[] fids) {
        SubFile sf;
        for (String fid : fids) {
            sf = new SubFile();
            sf.setFid(fid);
            sf.setSid(sub.getId());
            sf.setGmtCreate(LocalDateTime.now());
            sf.setGmtModified(LocalDateTime.now());
            subFileMapper.insert(sf);
        }
    }
}
