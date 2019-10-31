package cn.tycoding.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wangqing
 * @date 2019-10-18
 */

@Controller
@RequestMapping("/uploadAndDownload")
public class UploadAndDownloadFileController {
    @RequestMapping("/index2")
    public String index() {
        return "home/index2";
    }

    @RequestMapping(value = "/uploadFileAction", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileAction (@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadAndDownload");
        InputStream fis = null;
        OutputStream outputStream = null;
        try {
            fis = uploadFile.getInputStream();
            System.out.println("上传成功");
            outputStream = new FileOutputStream("./db/" + uploadFile.getOriginalFilename());
            IOUtils.copy(fis, outputStream);
            modelAndView.addObject("success", "上传成功");
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        modelAndView.addObject("fail", "上传失败！");
        return "fail";

    }

    @RequestMapping("downloadFileAction")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;

        try {
            File file = new File("./db/sys_data.sql");
            System.out.println("下载成功");
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attchment; fileName=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
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
