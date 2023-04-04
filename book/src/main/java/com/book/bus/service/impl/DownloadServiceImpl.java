package com.book.bus.service.impl;

import com.book.bus.aop.HttpAspect;
import com.book.bus.domain.Chapter;
import com.book.bus.service.DownloadService;
import com.book.bus.service.IChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * 小说下载实现类
 * @author coffee
 */
@Service
public class DownloadServiceImpl extends HttpServlet implements DownloadService {

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Resource
    private IChapterService iChapterService;


    @Override
    public void download(int fiction_id, String fileName, HttpServletResponse response) {

        String path = fileName + ".txt";
        FileWriter fileWriter=null;
        InputStream is=null;
        OutputStream os=null;
        try {
            //写入文件
            File file = new File(path);
            if (!file.exists()) {
                QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("fiction_id",fiction_id);
                List<Chapter> list = iChapterService.list(queryWrapper);
                fileWriter = new FileWriter(file);
                int i=0;
                for (Chapter chapter : list) {
                    //获取文章标题
                    fileWriter.write(chapter.getChapterTitle());
                    //获取文章内容
                    Chapter cp=iChapterService.getById(list.get(i).getId());
                    //过滤html
                    fileWriter.flush();
                    i++;
                }
                //关闭输入流
                fileWriter.close();
            }
            //向浏览器传输文件流
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".txt", "UTF-8"));
            //转换成流
            is = new FileInputStream(path);
            //以字节的形式去流
            os = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer))>0) {
                os.write(buffer, 0, len);
            }
            log.info("下载小说："+fileName);
        }catch (Exception e){
            log.error("下载异常："+e.getMessage());
        }finally {
            if (is!=null){
                try {
                    is.close();
                }catch (Exception e) {
                    log.error("关闭InputStream异常："+e.getMessage());
                }
            }
            if (os!=null){
                try {
                    os.close();
                }catch (Exception e){
                    log.error("关闭InputStream异常："+e.getMessage());
                }
            }
            try {
                if (fileWriter!=null){
                    response.getOutputStream().close();
                }
            }catch (Exception e){
                log.error("关闭InputStream异常："+e.getMessage());
            }
        }
    }
}
