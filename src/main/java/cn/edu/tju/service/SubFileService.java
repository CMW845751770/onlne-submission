package cn.edu.tju.service;

import cn.edu.tju.entity.File;
import cn.edu.tju.entity.SubFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-28
 */
public interface SubFileService extends IService<SubFile> {

    List<File> selectBySid(Integer id);
}
