package cn.edu.tju.service.impl;

import cn.edu.tju.entity.File;
import cn.edu.tju.entity.SubFile;
import cn.edu.tju.mapper.SubFileMapper;
import cn.edu.tju.service.SubFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-28
 */
@Service
public class SubFileServiceImpl extends ServiceImpl<SubFileMapper, SubFile> implements SubFileService {

    @Autowired
    private SubFileMapper subFileMapper;
    @Override
    public List<File> selectBySid(Integer id) {
        return subFileMapper.selectBySid(id);
    }
}
