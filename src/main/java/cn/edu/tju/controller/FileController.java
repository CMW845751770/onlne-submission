package cn.edu.tju.controller;


import cn.edu.tju.entity.ResponseData;
import cn.edu.tju.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/admin/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private PropertySource propertySourceBean;
    /*@Autowired
    private DocumentConverter converter;*/

    @ResponseBody
    @PostMapping("/upload")
    public Object upload(MultipartFile file) {
        cn.edu.tju.entity.File fileEntity = fileService.save(file);
        ResponseData<Object> result = new ResponseData<>(0, "上传成功");
        result.setData(fileEntity.getId());
        return result;
    }

    @GetMapping("/down/{id}")
    public Object down(@PathVariable String id, HttpServletResponse response) throws UnsupportedEncodingException {
        String src = "d:/";
        if (propertySourceBean.getProperty("file.upload.src") != null) {
            src = (String) propertySourceBean.getProperty("file.upload.src");
        }
        cn.edu.tju.entity.File fileEntity = fileService.getById(id);
        File  file = new File (src + "/" + fileEntity.getId() + "." + fileEntity.getFiletype());
        if (file.exists()) { //判断文件父目录是否存在
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileEntity.getFilename(),"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            OutputStream os = null; //输出流
            BufferedInputStream bis = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

   /* @GetMapping("/down2/{id}")
    public void down2(@PathVariable String id, HttpServletResponse response) throws IOException, OfficeException {
        String src = "d:/";
        if (propertySourceBean.getProperty("file.upload.src") != null) {
            src = (String) propertySourceBean.getProperty("file.upload.src");
        }
        File fileEntity = fileService.getById(id);
        java.io.File  file = new java.io.File (src + "/" + fileEntity.getId() + "." + fileEntity.getFiletype());
        if (file.exists()) { //判断文件父目录是否存在
            //生成pdf文件
            String pdfDir = "d:/test/pdf/" ;
            java.io.File newFile = new java.io.File(pdfDir);//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            //文件转化
            String fileName = file.getName().substring(0,file.getName().lastIndexOf("."))+".pdf" ;
            converter.convert(file).to(new java.io.File(pdfDir+fileName)).execute();
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(new java.io.File(pdfDir+fileName));// 读取文件
            IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();
        }
    }*/
}
