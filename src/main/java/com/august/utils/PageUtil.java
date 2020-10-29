package com.august.utils;

import com.august.vo.response.PageVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/22
 */
public class PageUtil {
    private PageUtil(){}
    public static <T> PageVO getPageVO(List<T> list){
        PageVO<T> pageVO = new PageVO<>();
        if (list instanceof Page){
            Page page = (Page)list;
            pageVO.setTotalRows(page.getTotal());
            pageVO.setList(page.getResult());
            pageVO.setTotalPages(page.getPages());
            pageVO.setCurPageSize(page.size());
            pageVO.setPageNum(page.getPageNum());
            pageVO.setPageSize(page.getPageSize());
        }
        return pageVO;
    }
}
