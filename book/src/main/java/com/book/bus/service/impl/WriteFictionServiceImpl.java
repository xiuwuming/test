package com.book.bus.service.impl;


import com.book.bus.aop.HttpAspect;
import com.book.bus.domain.Chapter;
import com.book.bus.domain.ChapterContent;
import com.book.bus.domain.Fiction;
import com.book.bus.mapper.ChapterContentMapper;
import com.book.bus.mapper.ChapterMapper;
import com.book.bus.service.IFictionService;
import com.book.bus.service.WriteFictionService;
import com.book.bus.utils.JsoupUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 小说抓取实现类
 */
@Service
public class WriteFictionServiceImpl implements WriteFictionService {

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Resource
    private IFictionService iFictionService;

    @Resource
    private ChapterContentMapper chapterContentMapper;

    @Resource
    private ChapterMapper chapterMapper;


    /**
     * 抓取小说持久化到mysql数据库
     * @param fictionURL
     */
    @Async
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public   void insert(String fictionURL) {

        //爬取小说基本信息
        Fiction fiction = getFictions(JsoupUtil.getDoc(fictionURL));
        if (fiction != null) {
            //查询数据库信息
            QueryWrapper<Fiction> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("fiction_name",fiction.getFictionName());
            Fiction ft = iFictionService.getOne(queryWrapper);
            if (ft == null) {
                //设置url
                fiction.setFictionUrl(fictionURL);
                iFictionService.save(fiction);
                Document doc = JsoupUtil.getDoc(fictionURL);
                Elements elements = doc.select("dd>a");
                log.info("小说章数：" + elements.size());
                //前面12章节重复
                int k = 12;
                //数据库排序
                int sort = 1;
                //前面12章节重复
                int j = elements.size() - 12;
                ChapterContent chapterContent=new ChapterContent();
                for (int i = 0; i < j; i++) {
                        try {
                            String url = elements.get(k).attr("abs:href");
                            Document document = JsoupUtil.getDoc(url);
                            //章节标题
                            String title = document.select("h1").text();
                            //章节内容
                            String text = document.getElementById("content").html();
                            //保存小说内容
                            chapterContentMapper.insert(chapterContent.setContent(text));
                            int id = chapterContent.getId();
                            chapterMapper.insert(new Chapter(url,fiction.getId(), title, sort,id));
                            log.info("抓取小说：" + fiction.getFictionName() + "" + title);
                            sort++;
                            k++;
                        }catch (Exception e){
                            log.error("抓取小说内容异常"+e);
                        }
                }
                log.info("抓取小说完成");
            } else {
                log.info("这本小说已经保存到数据库");
            }
        } else {
            log.info("小说连接地址错误");
        }
    }

    /**
     * 获取书趣阁小说基本信息
     * @param document
     * @return
     */
    @Override
    public Fiction getFictions(Document document) {
        boolean flag = false;
            try {
                String fictionName = document.select("meta[property=og:novel:book_name]").attr("content");
                String brief = document.select("meta[property=og:description]").attr("content");
                String author = document.select("meta[property=og:novel:author]").attr("content");
                String type = document.select("meta[property=og:novel:category]").attr("content");
                String newest = document.select("meta[property=og:novel:latest_chapter_name]").attr("content");
                String state = document.select("meta[property=og:novel:status]").attr("content");
                String img = document.select("#fmimg > img").attr("abs:src");
                //小说基本信息
                Elements small = document.select("div.small");
                //字数
//                String number = JsoupUtil.sub(small.get(0).child(3).text());
                return new Fiction("", img, brief, fictionName, author, type, newest, state, "");
            } catch (Exception e) {
                log.error("获取小说基本信息失败"+e);
            }
        return null;
    }
}
