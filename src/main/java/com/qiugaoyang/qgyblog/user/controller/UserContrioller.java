package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.util.ImgUtil;
import com.qiugaoyang.qgyblog.common.util.MD5Util;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserContrioller {
    @Resource
    private LoginRegisterService loginRegisterService;


    //
    //    修改个人信息
    @PostMapping("/upd/info")
    public String updUserInfo(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "userHeadImgPic", required = false) MultipartFile multipartFile,
            @Valid User userInfo,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) return "redirect:/getui/user/login/";
        if (user.getUserState() != 1) {
            redirectAttributes.addFlashAttribute("修改失败 可能是亲尚未激活 请联系管理员");
            return "redirect:/getui/user/userinfo/";
        }
        user = loginRegisterService.getByUserId(user.getUserId());
//        校验是否有参数修改
//
        if (!(userInfo.getUserRealName() == null || userInfo.getUserRealName().trim().equals(""))) {
            if (user.getUserRealName() == null || !userInfo.getUserRealName().trim().equals(user.getUserRealName())) {
                user.setUserRealName(userInfo.getUserRealName());
            }
        }
        if (!(userInfo.getUserDesc() == null || userInfo.getUserDesc().trim().equals(""))) {
            if (user.getUserDesc() == null || !userInfo.getUserDesc().trim().equals(user.getUserDesc())) {
                user.setUserDesc(userInfo.getUserDesc());
            }
        }
        if (multipartFile == null) {

        } else {
            String filename = multipartFile.getOriginalFilename();
//                文件类型
            String contentType = multipartFile.getContentType();
            if (!contentType.matches("^image/.*")) {
                redirectAttributes.addFlashAttribute("message", "请上传图片类型");
                return "redirect:/getui/user/userinfo/";
            }
            long size = multipartFile.getSize(); //   单位字节
            if (size > (0.512 * 1024 * 1024)) {
                redirectAttributes.addFlashAttribute("message", "图片过大 请上传512kb以内的图片");
                return "redirect:/getui/user/userinfo/";
            }
//                重命名图片文件
            String name = ImgUtil.upName(filename);
//                转存文件路径
            File filePath = new File(Params.USER_IMG_PATH + user.getUserId() + "//");
//如果路径不存在
            if (!filePath.exists()) filePath.mkdirs();

//                保存文件
            File file = new File(filePath, name);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
//                生成图片访问路径
            String url = "/qgyblog/user/img/get/" + user.getUserId() + "?imgName=" + name;
//            保存到数据库
            user.setUserHeadImg(url);
        }
        User save = loginRegisterService.updUser(user);

        if (save != null) {
            user.setUserPassword("");
            request.getSession().setAttribute(Params.USER_SESSION_LOGIN_KEY, user);
            redirectAttributes.addFlashAttribute("message", "修改成功");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        }

        return "redirect:/getui/user/userinfo/";
    }


    //    修改密码
    @PostMapping("/upd/psw")
    public String updUserPasword(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "userPassword", required = false) String userPassword,
            @RequestParam(value = "updPassword", required = false) String updPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        if (userPassword == null || updPassword == null || confirmPassword == null || updPassword.trim().equals("") || userPassword.trim().equals("") || confirmPassword.trim().equals("")) {
            return "redirect:/getui/user/404/";
        }
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) return "redirect:/getui/user/login/";
        if (user.getUserState() != 1) {
            redirectAttributes.addFlashAttribute("修改失败 可能是亲尚未激活 请联系管理员");
            return "redirect:/getui/user/userinfo/";
        }

//        查询用户数据 checkLogin 会删除passworld
        user = loginRegisterService.getByUserId(user.getUserId());


        if (!MD5Util.GetMD5Code2(userPassword).trim().equals(user.getUserPassword())) {
            redirectAttributes.addFlashAttribute("修改失败");
            return "redirect:/getui/user/userinfo/";
        }

        if (!confirmPassword.equals(updPassword)) {
            redirectAttributes.addFlashAttribute("修改失败 请确认输入");
            return "redirect:/getui/user/userinfo/";
        }
        user.setUserPassword(MD5Util.GetMD5Code2(updPassword));
        User save = loginRegisterService.updUser(user);
        if (save != null) {
// 注销登录
            loginRegisterService.exitLogin(userKey, userToken, request, response);
            redirectAttributes.addFlashAttribute("message", "修改成功");
            return "redirect:/getui/user/login/";
        } else {
            redirectAttributes.addFlashAttribute("message", "修改失败");
            return "redirect:/getui/user/login/";
        }
    }
}
