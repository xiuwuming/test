package com.book.bus.service.impl;

import com.book.bus.aop.HttpAspect;
import com.book.bus.common.DataGridView;
import com.book.bus.domain.Fiction;
import com.book.bus.mapper.FictionMapper;
import com.book.bus.service.IFictionService;
import com.book.bus.vo.FictionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
@Service
public class FictionServiceImpl extends ServiceImpl<FictionMapper, Fiction> implements IFictionService {

    /**
     * 默认参数
     */
    private final static Integer CURR = 1;
    private final static Integer LIMIT = 20;
    private final static Integer VIEW_TYPE = 2;
    private final static Integer TYPE = 0;


    @Resource
    private IFictionService iFictionService;

    @Resource
    private FictionMapper fictionMapper;

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Override
    public List<Fiction> queryLike(String v) {
        QueryWrapper<Fiction> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("fiction_name", v);
        return list(queryWrapper);
    }


    @Override
    public DataGridView queryAllFiction(FictionVo fictionVo) {

        //当前页和页数
        if (null == fictionVo.getCurr()) {
            fictionVo.setCurr(CURR);
            fictionVo.setLimit(LIMIT);
        }
        //图片模式或者列表模式
        Integer viewType = null;
        if (null == fictionVo.getViewType()) {
            viewType = VIEW_TYPE;
        } else {
            viewType = fictionVo.getViewType();
        }
        if (null == fictionVo.getType()) {
            fictionVo.setType(TYPE);
        }
        //构建分页
        Page<Fiction> page = new Page<>(fictionVo.getCurr(), fictionVo.getLimit());
        QueryWrapper<Fiction> qw = new QueryWrapper<>();
        //分类查询
        if (null != fictionVo.getType() && !fictionVo.getType().equals(TYPE)) {
            qw.eq("type", FictionVo.getTypeName(fictionVo.getType()));
        }
        //写作进度查询
        if (null != fictionVo.getState() && !fictionVo.getState().equals(TYPE)) {
            qw.eq( "state", FictionVo.getState(fictionVo.getState()));
        }
        //字数查询
        if (null != fictionVo.getNumber() && !fictionVo.getNumber().equals(TYPE)) {
            int a=1,b=2,c=3,d=4,e=5;
            if (fictionVo.getNumber().equals(a)) {
                qw.lt("number", 300000);
            }
            if (fictionVo.getNumber().equals(b)) {
                qw.gt("number",300000).lt("number", 500000);
            }
            if (fictionVo.getNumber().equals(c)) {
                qw.gt("number",500000).lt("number", 1000000);
            }
            if (fictionVo.getNumber().equals(d)) {
                qw.gt("number",1000000).lt("number", 2000000);
            }
            if (fictionVo.getNumber().equals(e)) {
                qw.gt("number",2000000);
            }
        }
        //时间段查询
        if (null != fictionVo.getTime() && !fictionVo.getTime().equals(TYPE)) {
            DateTime today = new DateTime();
            int a=1,b=2,c=3,d=4;
            if (fictionVo.getTime().equals(a)){
                qw.ge("create_date",today.minusDays(3).toDate());
            }
            if (fictionVo.getTime().equals(b)){
                qw.ge("create_date",today.minusDays(7).toDate());
            }
            if (fictionVo.getTime().equals(c)){
                qw.ge("create_date",today.minusDays(15).toDate());
            }
            if (fictionVo.getTime().equals(d)){
                qw.ge("create_date",today.minusDays(30).toDate());
            }
        }
        qw.orderByDesc("create_date");
        this.fictionMapper.selectPage(page, qw);
        //获取分页后查询出的记录
        List<Fiction> records = page.getRecords();
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), records, viewType, fictionVo.getType());
    }

    public static void main(String[] args) {
        DateTime today = new DateTime();
        System.out.println(today.minusDays(3));
    }
    @Override
    public void addView(Fiction fiction) {
        int i = fiction.getViews();
        int v = i + 1;
        fiction.setViews(v);
        iFictionService.updateById(fiction);
    }
}
