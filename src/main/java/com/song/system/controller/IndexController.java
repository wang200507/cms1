package com.song.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-20
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "login";
    }
}
