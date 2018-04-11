package com.imooc.web.controller;

import com.imooc.dto.FileInfo;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {
    private String folder="F:\\space\\STS_space\\imooc-security-demo\\src\\main\\java\\com\\imooc\\web\\controller";


    /**
     *
     * @param file 与测试用例所传必须相同
     * @return
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        File localFile = new File(folder, new Date().getTime() + ".txt");
        //传入文件写入新建文件
        file.transferTo(localFile);
        //AbsolutePath是绝对路径
        return new FileInfo(localFile.getAbsolutePath());


    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id , HttpServletRequest request , HttpServletResponse response) throws Exception {
        //JDK7新特性：流放入括号中，不用再finally主动关闭流，自动关闭
        try (InputStream inputStream=new FileInputStream(new File(folder,id+".txt"));
        		OutputStream outputStream=response.getOutputStream();){
            //设置浏览器响应内容类型
            response.setContentType("application/x-download");
            //设置下载到本地的名称
            response.addHeader("Content-Disposition","attachment;filename=test.txt");

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();

        }
    }
}
