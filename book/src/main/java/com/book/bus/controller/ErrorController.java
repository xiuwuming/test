package com.book.bus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 异常控制器
 * @author 追风
 */
@Controller
@RequestMapping("error")
public class ErrorController {

    /**
     * 返回一本小说
     * @return
     */
    @RequestMapping("404")
    public String error() {

        return "error/404";
    }
}