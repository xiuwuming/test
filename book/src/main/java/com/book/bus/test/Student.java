package com.book.bus.test;

/**
 * 接着定义一个学生接口，学生当然是解决问题，但是接收一个Callback参数，这样学生就知道解决完毕问题向谁报告：
 */
public interface Student {


    //回答问题
     void resolveQuestion(Callback callback);

}
