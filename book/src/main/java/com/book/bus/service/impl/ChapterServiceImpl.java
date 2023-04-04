package com.book.bus.service.impl;

import com.book.bus.domain.Chapter;
import com.book.bus.mapper.ChapterMapper;
import com.book.bus.service.IChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {

    @Override
    public List<Chapter> queryByFictionIdList(int fictionId) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fictionId);
        return list(queryWrapper);
    }

    @Override
    public Chapter netChapter(int fiction_id, int sort) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fiction_id);
        queryWrapper.eq("sort",sort);
        return getOne(queryWrapper);
    }

}
