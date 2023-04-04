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
public class Chapter implements Serializable {
    public Chapter() {
    }

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer fictionId;

    private String chapterTitle;

    private Timestamp createDate;

    private Integer sort;

    private Integer contentId;

    private String chapterUrl;

    public Chapter(String chapterUrl,int fictionId, String chapterTitle, Integer sort,Integer contentId) {
        this.fictionId = fictionId;
        this.chapterUrl = chapterUrl;
        this.chapterTitle = chapterTitle;
        this.createDate = TimeUtil.getTimestamp();
        this.sort = sort;
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", fictionId=" + fictionId +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", createDate=" + createDate +
                ", sort=" + sort +
                ", content_id=" + contentId +
                ", chapterUrl='" + chapterUrl + '\'' +
                '}';
    }
}
