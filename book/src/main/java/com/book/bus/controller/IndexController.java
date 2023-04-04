package com.book.bus.controller;

import com.book.bus.common.DataGridView;
import com.book.bus.domain.Fiction;
import com.book.bus.service.IFictionService;
import com.book.bus.vo.FictionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页 控制器
 * @author coffee
 */
@Controller
public class IndexController {

    private final IFictionService iFictionService;


    @Autowired
    public IndexController(IFictionService iFictionService) {
        this.iFictionService = iFictionService;
    }

    /**
     * 首页
     */
    @RequestMapping(value = "index")
    public String index(FictionVo fictionVo, Model model) {
        DataGridView dataGridView = iFictionService.queryAllFiction(fictionVo);
        model.addAttribute("data",dataGridView);
        return "index/index";
    }

    /**
     * 小说查询
     *
     */
    @RequestMapping(value = "search")
    public String  search( Model model, FictionVo fictionVo) {

        String fictionName = fictionVo.getFictionName();
        model.addAttribute("fictionName",fictionName);
        List<Fiction> list = iFictionService.queryLike(fictionName);
        model.addAttribute("list",list);
        return "index/search";
    }
}
