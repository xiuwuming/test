package com.book.bus.controller;


import com.book.bus.aop.HttpAspect;
import com.book.bus.service.WriteFictionService;
import com.book.bus.utils.JsoupUtil;
import com.book.bus.utils.Result;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抓取小说 控制器
 */
@Controller
@RequestMapping("sys")
public class FictionController {

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    private final WriteFictionService writeFiction;

    @Autowired
    public FictionController(WriteFictionService writeFiction) {
        this.writeFiction = writeFiction;
    }


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {

        return "fiction/sys";
    }

    /**
     * 抓取单本小说
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "write", method = RequestMethod.POST)
    public Result fiction_write(@RequestParam("fiction_url") String url) {
            try {
                boolean result = JsoupUtil.isConnection(url);
                if (result) {
                    writeFiction.insert(url);
                    return new Result(200, "小说地址解析成功!");
                } else {
                    log.info("FictionController/fiction_write:地址解析失败");
                    return new Result(500, "小说地址解析失败,抓取失败!");
                }
            }catch (Exception e) {
                log.info("单本抓取小说失败"+e);
            }
        return new Result(500, "小说地址解析失败,抓取失败!");
    }

    /**
     * 批量抓取小说
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "writes", method = RequestMethod.POST)
    public Result fiction_writes(@RequestParam("fiction_urls") String url) {
        boolean result = JsoupUtil.isConnection(url);
        if (result) {
                try {
                    Document document = JsoupUtil.getDoc(url);
                    Element element = document.select("div.l").first();
                    Elements elements = element.select("span.s2");
                    for (Element element1 : elements) {
                        String string = element1.select("a").attr("abs:href");
                        writeFiction.insert(string);
                    }
                }catch (Exception e){
                    log.info("批量抓取小说异常"+e);
                }
            return new Result(200, "小说地址解析成功!");
        } else {
            log.info("FictionController/fiction_write:地址解析失败");
            return new Result(500, "小说地址解析失败,抓取失败!");
        }
    }
}