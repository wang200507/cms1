package com.song.system.controller;

import com.song.entity.User;
import com.song.repository.UserRepositoty;
import com.song.service.UserService;
import com.song.util.Constants;
import com.song.util.Result.ExceptionMsg;
import com.song.util.Result.Response;
import com.song.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Autowired
    private UserRepositoty userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(@SessionAttribute(Constants.USER_SESSION_KEY) String account, Model model) {
        model.addAttribute("name", account);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginPost",method = RequestMethod.POST)
    @ResponseBody
    public Response loginPost(User user, HttpSession session,HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String url = "";
        User u = null;
        if(Utility.isNotEmpty(user.getUserCode()) && Utility.isNotEmpty(user.getPassword())){
            u = userService.findUserByName(user.getUserCode());

            if(Utility.isNotEmpty(u)){
                String _pass = u.getPassword();
                if(_pass.equals(getPwd(user.getPassword()))){
                    // 设置session
                    session.setAttribute(Constants.USER_SESSION_KEY, u);

                    Cookie cookie = new Cookie(Constants.USER_SESSION_KEY, cookieSign(String.valueOf(u.getId())));
                    cookie.setMaxAge(Constants.COOKIE_TIMEOUT);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return result(ExceptionMsg.SUCCESS);
                }else{
                    return result(ExceptionMsg.PassWordError);
                }

            }else{
                return result(ExceptionMsg.LoginNameNotExists);
            }
        }else{
            return result(ExceptionMsg.LoginNameOrPassWordError);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(Constants.USER_SESSION_KEY);
        return "redirect:/login";
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public Response updatePassword( String oldPassword,  String newPassword) {
        try {
            User user = getUser();
            String password = user.getPassword();
            String newpwd = getPwd(newPassword);
            if(password.equals(getPwd(oldPassword))){
                userRepository.setNewPassword(newpwd, user.getUserCode());
                user.setPassword(newpwd);
                getSession().setAttribute(Constants.USER_SESSION_KEY, user);
            }else{
                return result(ExceptionMsg.PassWordError);
            }
        } catch (Exception e) {
            logger.error("updatePassword failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }
}
