package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;

    @Value("${FTP_PORT}")
    private Integer FTP_PORT;

    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;

    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;

    @Value("${FTP_BASEPATH}")
    private String FTP_BASEPATH;

    @Value("${IMAGES_BASE_URL}")
    private String IMAGES_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 生成一个新文件,取原始文件的名i在
            String oldname = uploadFile.getOriginalFilename();
            String newname = IDUtils.genImageName();
            newname = newname + oldname.substring(oldname.lastIndexOf("."));
            String imagePath = new DateTime().toString("/yyyy/MM/dd");
            Boolean flag = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    FTP_BASEPATH, imagePath, newname, uploadFile.getInputStream());
            if (!flag) {
                map.put("error", 1);
                map.put("message", "文件上传失败");
                return map;
            }
            map.put("error", 0);
            map.put("url", IMAGES_BASE_URL + imagePath + "/" + newname);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", "文件上传发生异常");
            return map;
        }

    }
}
