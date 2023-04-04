package com.book;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@MapperScan("com.book.bus.mapper")
@EnableScheduling
public class BookApplication {

    public final static Logger log = LoggerFactory.getLogger(BookApplication.class);

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(BookApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");

        log.info("\n----------------------------------------------------------\n\t" +
                "程序启动成功! URLs:\n\t" +
                "首页: \t\thttp://localhost:" + port + path + "/index"+"\n\t" +
                "爬虫地址: \thttp://" + ip + ":" + port + path +"/sys/index"+"\n\t" +
                "----------------------------------------------------------");
    }

}
