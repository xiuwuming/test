package com.book.bus.service;

import com.book.bus.common.DataGridView;
import com.book.bus.domain.Fiction;
import com.book.bus.vo.FictionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
public interface IFictionService extends IService<Fiction>{

    /**
     * 模糊查询
     * @param v 字符串
     * @return list
     */
    List<Fiction> queryLike(String v);

    /**
     *
     * @param fictionVo vo
     * @return
     */
    DataGridView queryAllFiction(FictionVo fictionVo);

    /**
     * 小说点击量
     */
  void   addView(Fiction fiction);

}
