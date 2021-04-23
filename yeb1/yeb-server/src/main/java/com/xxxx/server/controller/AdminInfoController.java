package com.xxxx.server.controller;

import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.PictureUtil;
import com.xxxx.server.utils.QiniuUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class AdminInfoController {

    @Resource
    private IAdminService adminService;


    //上传头像,可用
    @ApiOperation(value = "上传头像")
    @PostMapping("/admin/userface")
    public RespBean updateAdminUserFace(@RequestParam("file") MultipartFile multipartFile, Integer id, Authentication authentication) throws IOException {

        if (!multipartFile.isEmpty()) {
            FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
            String path = QiniuUtils.uploadQNImg(inputStream, PictureUtil.getRandomFileName()); // KeyUtil.genUniqueKey()生成图片的随机名
            System.out.print("七牛云返回的图片链接:" + path);
            return adminService.updateAdminUserFace(path,id,authentication);
        }
        return RespBean.error("修改失败");
    }

}
