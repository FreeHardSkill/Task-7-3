package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex1() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndex2() {
        return "index";
    }

    @RequestMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        // ничего не делаем
    }
}
