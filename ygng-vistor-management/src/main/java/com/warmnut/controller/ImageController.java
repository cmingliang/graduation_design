package com.warmnut.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 图片相关
 * @author qy
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/image")
public class ImageController {
	@Value("${config.faceImagePath}")
	private String faceImagePath;
	/**
	 * 获取图片
	 * @param iamgeName
	 */
	@GetMapping("/{imageName}")
	public void getImage(@PathVariable String imageName,HttpServletRequest request, HttpServletResponse response) {
		
	        String fileUrl = faceImagePath+imageName;
	        if (fileUrl != null) {
	            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
	           /* String realPath = request.getServletContext().getRealPath(
	                    "//WEB-INF//");*/
	            /*File file = new File(realPath, fileName);*/
	            File file = new File(fileUrl);
	            if (file.exists()) {
	                response.setContentType("image/jpeg");
//	                response.addHeader("Content-Disposition","attachment;fileName=" + imageName);// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    int i = bis.read(buffer);
	                    while (i != -1) {
	                        os.write(buffer, 0, i);
	                        i = bis.read(buffer);
	                    }
	                    System.out.println("success");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    if (bis != null) {
	                        try {
	                            bis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    if (fis != null) {
	                        try {
	                            fis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            }
	        }
	       

		
	}

}
