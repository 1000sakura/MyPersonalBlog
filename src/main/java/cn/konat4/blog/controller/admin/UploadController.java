package cn.konat4.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.konat4.blog.config.Constants;
import cn.konat4.blog.util.MyBlogUtils;
import cn.konat4.blog.util.Result;
import cn.konat4.blog.util.ResultGenerator;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UploadController {

    @PostMapping({"/upload/file"})
    @ResponseBody
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Result<?> upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
        System.out.println("File Directory Name: " + fileDirectory.getName());
        System.out.println("AbsolutePath Of Dir: " + fileDirectory.getAbsolutePath());
        System.out.println(Constants.FILE_UPLOAD_DIC);
        
        File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
        System.out.println(destFile.getAbsolutePath());
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData((MyBlogUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName));
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

}
