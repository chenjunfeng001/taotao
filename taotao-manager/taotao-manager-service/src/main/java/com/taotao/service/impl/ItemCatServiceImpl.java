package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    /** 通过父节点查找所有子节点 */
    public List<TbItemCat> getItemCatList(Long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        Criteria criteria = itemCatExample.createCriteria();
        // 拼接条件
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(itemCatExample);
        return list;
    }

}
