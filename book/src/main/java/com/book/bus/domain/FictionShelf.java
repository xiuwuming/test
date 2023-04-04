package com.book.bus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 追风
 * @since 2020-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FictionShelf implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer fictionId;

    private Integer sort;

    private Integer userId;

    @Override
    public String toString() {
        return "FictionShelf{" +
                "id=" + id +
                ", fictionId=" + fictionId +
                ", sort=" + sort +
                ", userId=" + userId +
                '}';
    }
}
