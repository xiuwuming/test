package com.book.bus.domain;

import com.book.bus.utils.TimeUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@NoArgsConstructor
public class Fiction implements Serializable {
    public Fiction() {
    }

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String fictionName;

    private Timestamp createDate;

    private String author;

    private String type;

    private String newest;

    private String state;

    private int views; //访问量

    private String number;

    private String brief;

    private String imgUrl;

    private String fictionUrl;

    public Fiction(String fictionUrl,String imgUrl,String brief,String fictionName, String author, String type, String newest, String state, String number) {
        this.fictionName = fictionName;
        this.createDate = TimeUtil.getTimestamp();
        this.author = author;
        this.type = type;
        this.imgUrl = imgUrl;
        this.newest = newest;
        this.state = state;
        this.brief = brief;
        this.number = number;
        this.fictionUrl = fictionUrl;
    }

    @Override
    public String toString() {
        return "Fiction{" +
                "id=" + id +
                ", fictionName='" + fictionName + '\'' +
                ", createDate=" + createDate +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", newest='" + newest + '\'' +
                ", state='" + state + '\'' +
                ", views=" + views +
                ", number='" + number + '\'' +
                ", brief='" + brief + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", fictionUrl='" + fictionUrl + '\'' +
                '}';
    }
}
