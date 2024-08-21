package com.yupi.muapiinterface.controller;

import com.yupi.muapiclientsdk.model.User;
import com.yupi.muapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 *
 * @author yupi
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name) {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 从请求头中获取名为 "accessKey" 的值
        String accessKey = request.getHeader("accessKey");
        // 从请求头中获取名为 "secretKey" 的值
//        String secretKey = request.getHeader("secretKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // 如果 accessKey 不等于 "yupi" 或者 secretKey 不等于 "abcdefgh"
        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!accessKey.equals("fang")){
            // 抛出一个运行时异常，表示权限不足
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        // todo 时间和当前时间不能超过 5 分钟
//        if (timestamp){
//
//        }
        // todo 实际情况中是从数据库中查出 secretKey
        String serverSign = SignUtils.genSign(body, "abcdefgh");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }
        // 如果权限校验通过，返回 "POST 用户名字是" + 用户名
        return "POST 用户名字是" + user.getUsername();
    }

}

