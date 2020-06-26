package cn.edu.tju.service.impl;

import cn.edu.tju.entity.File;
import cn.edu.tju.mapper.FileMapper;
import cn.edu.tju.service.FileService;
import cn.edu.tju.utils.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CMW天下第一
 * @since 2020-03-28
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Autowired
    private PropertySource propertySourceBean;
    @Autowired
    private FileMapper fileMapper;

    @Override
    @Transactional
    public File save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        int i = filename.lastIndexOf('.');
        String filetype = filename.substring(i+1);

        File fileEntity = new File();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setFiletype(filetype);
        fileEntity.setFilesize(file.getSize());
        fileEntity.setGmtCreate(LocalDateTime.now());
        fileEntity.setGmtModified(LocalDateTime.now());
        fileEntity.setStatus(0);
        fileMapper.insert(fileEntity);

        String src="d:/";
        if (propertySourceBean.getProperty("file.upload.src") != null) {
            src = (String)propertySourceBean.getProperty("file.upload.src");
        }

        try {
            FileUtil.uploadFile(file.getBytes(),src,fileEntity.getId()+"."+fileEntity.getFiletype());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileEntity;
    }
}
