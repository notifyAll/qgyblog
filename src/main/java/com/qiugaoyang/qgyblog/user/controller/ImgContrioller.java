package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.resultbean.ImgPathResult;
import com.qiugaoyang.qgyblog.common.util.ImgUtil;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/user/img")
public class ImgContrioller {


    @Resource
    LoginRegisterService loginRegisterService;
    /**
     * 接收图片
     * @return
     */
    @PostMapping("/add")
    public ImgPathResult addImg(
            @RequestParam("imgFiles") MultipartFile[] multipartFiles,
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

//      1  校验是否已经登录 获取用户数据
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
//        未登录
        if (user==null) return  new ImgPathResult(1, null);

        try {

// 返回的图片地址
            ArrayList<String> data = new ArrayList<String>();

            for (int i = 0; i < multipartFiles.length; i++) {
//                System.out.println(multipartFiles[i].getName());
                String filename = multipartFiles[i].getOriginalFilename();
//                文件类型
                String contentType = multipartFiles[i].getContentType();

//                重命名图片文件
                String name = ImgUtil.upName(filename);
//                转存文件路径
                File filePath = new File(Params.USER_IMG_PATH+user.getUserId()+"//");
//如果路径不存在
                if (!filePath.exists()) filePath.mkdirs();

//                保存文件
                File file = new File(filePath, name);
                multipartFiles[i].transferTo(file);

//                生成图片访问路径
                String url = "/qgyblog/user/img/get/"+user.getUserId()+"?imgName=" + name;
                data.add(url);
            }
//            响应数据
            return new ImgPathResult(0, data);
        } catch (IOException e) {
            e.printStackTrace();
            return new ImgPathResult(1, null);
        }

    }

    /**
     * 查询图片接口
     * /get/img?imgName=
     */
    @GetMapping("/get/{userId}")
    public String getImg(@RequestParam(value = "imgName",required = false) String imgName,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable("userId") Integer userId
    ) {
        if (userId==null || userId<0 ||imgName==null||imgName.trim().equals("")){
            return null;
        }

        File file = new File(Params.USER_IMG_PATH+userId+"//", imgName);

        try {
            FileInputStream fileInputStream = new FileInputStream(file); //输入流
            ServletOutputStream outputStream = response.getOutputStream();//输出流

            byte[] f = new byte[(int) file.length()];
//           读取图片
            fileInputStream.read(f);
            //向页面输出图片
            outputStream.write(f);

//            关闭流
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
