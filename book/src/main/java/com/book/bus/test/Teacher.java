package com.book.bus.test;

/**
 * 定义一个老师对象，实现Callback接口：
 */
public class Teacher implements Callback {

    //接口属性
    private Student student;

    //构造函数
    public Teacher(Student student) {
        this.student = student;
    }
    //提问问题
    public void askQuestion() {
        student.resolveQuestion(this);
    }
    //回调函数接口方法
    @Override
    public void tellAnswer(int answer) {
        System.out.println("知道了，你的答案是" + answer);
    }
}
