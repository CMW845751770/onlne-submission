package cn.edu.tju.service;

import cn.edu.tju.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-28
 */
public interface FileService extends IService<File> {
    File save(MultipartFile file);
}
