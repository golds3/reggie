package com.hbx.reggie.controller;

import com.hbx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 黄柏轩
 * @version 1.0
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String BasePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String hz = originalFilename.substring(originalFilename.lastIndexOf("."));
        File file1 = new File(BasePath);
        if(!file1.exists()){
            file1.mkdir();
        }

        String realname =UUID.randomUUID().toString() + hz;
        String filename = BasePath+realname;
        file.transferTo(new File(filename));
        return R.success(realname);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(BasePath+name));
            //设置响应文件类型
            response.setContentType("image/jpeg");
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
