package cn.edu.tju.mapper;

import cn.edu.tju.entity.File;
import cn.edu.tju.entity.SubFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-28
 */
public interface SubFileMapper extends BaseMapper<SubFile> {

    List<File> selectBySid(Integer id);
}
