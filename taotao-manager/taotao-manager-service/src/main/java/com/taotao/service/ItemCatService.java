package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TbItemCat;

public interface ItemCatService {

    /** 通过父节点查找所有子节点 */
    public List<TbItemCat> getItemCatList(Long parentId);
}
