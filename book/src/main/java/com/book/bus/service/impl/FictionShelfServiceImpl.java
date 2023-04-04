package com.book.bus.service.impl;

import com.book.bus.domain.Fiction;
import com.book.bus.domain.FictionShelf;
import com.book.bus.domain.User;
import com.book.bus.mapper.FictionShelfMapper;
import com.book.bus.service.IFictionService;
import com.book.bus.service.IFictionShelfService;
import com.book.bus.service.IUserService;
import com.book.bus.utils.FictionUtil;
import com.book.bus.utils.Result;
import com.book.bus.vo.ShelfVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 追风
 * @since 2020-01-05
 */
@Service
public class FictionShelfServiceImpl extends ServiceImpl<FictionShelfMapper, FictionShelf> implements IFictionShelfService {

    @Resource
    private FictionShelfMapper fictionShelfMapper;


    @Resource
    private IUserService iUserService;

    @Resource
    private IFictionService iFictionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addShelf(HttpServletRequest request, int fiction_id) {
        HttpSession session = request.getSession();
        if (null == session.getAttribute("id")) {
            return new Result(-1, "未登录");
        }
        int userId = (int) session.getAttribute("id");
        //写入书架
        FictionShelf fictionShelf = new FictionShelf();
        fictionShelf.setFictionId(fiction_id);
        fictionShelf.setSort(1);
        fictionShelf.setUserId(userId);
        fictionShelfMapper.insert(fictionShelf);
        //书架id
        int shelfId = fictionShelf.getId();

        //更新用户表
        User user = iUserService.getById(userId);
        if (null == user.getShelfIds()) {
            user.setShelfIds("");
        }
        user.setShelfIds(FictionUtil.addOne(user.getShelfIds(), String.valueOf(shelfId)));
        iUserService.updateById(user);
        return new Result(200, "加入书架成功");
    }

    @Override
    public int isShelf(HttpServletRequest request, int fictionId) {
        HttpSession session = request.getSession();
        if (null == session.getAttribute("id")) {
            return 0;
        }
        QueryWrapper<FictionShelf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fiction_id", fictionId);
        queryWrapper.eq("user_id", session.getAttribute("id"));

        FictionShelf fictionShelf = fictionShelfMapper.selectOne(queryWrapper);
        if (null == fictionShelf) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<ShelfVo> queryByUserId(int userId) throws Exception {
        QueryWrapper<FictionShelf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<FictionShelf> shelfList = fictionShelfMapper.selectList(queryWrapper);
        if (null == shelfList) {
            return null;
        }
        List<ShelfVo> list = new ArrayList<>();
        for (FictionShelf shelf : shelfList) {
            QueryWrapper<Fiction> fictionQueryWrapper = new QueryWrapper<>();
            fictionQueryWrapper.eq("id", shelf.getFictionId());
            Fiction ft = iFictionService.getOne(fictionQueryWrapper);
            list.add(new ShelfVo(shelf.getId(), ft.getId(), ft.getFictionName(), ft.getCreateDate(), ft.getAuthor(), ft.getType(), ft.getNewest(), ft.getState(), ft.getImgUrl(), ft.getBrief(), shelf.getSort()));
        }
        return list;
    }

    @Override
    public int updateShelf(int fictionId, int userId, int sort) {
        QueryWrapper<FictionShelf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fiction_id", fictionId);
        queryWrapper.eq("user_id", userId);
        FictionShelf fictionShelf = fictionShelfMapper.selectOne(queryWrapper);
        if (null == fictionShelf) {
            FictionShelf fictionShelf1 = new FictionShelf();
            fictionShelf1.setFictionId(fictionId);
            fictionShelf1.setSort(sort);
            fictionShelf1.setUserId(userId);
            return fictionShelfMapper.insert(fictionShelf1);
        } else {
            fictionShelf.setSort(sort);
            return fictionShelfMapper.updateById(fictionShelf);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteShelf(int id, int userId) {
        //从书架表删除
        int i = fictionShelfMapper.deleteById(id);
        //更新用户表
        User user = iUserService.getById(userId);
        user.setShelfIds(FictionUtil.removeOne(user.getShelfIds(), String.valueOf(id)));
        iUserService.updateById(user);
        return i;
    }
}
