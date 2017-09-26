package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    public CatResult getCatResult() {
        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0));
        return catResult;
    }

    /** 通过递归,将节点拼装需要的json */
    private List<?> getCatList(long parentId) {
        // 拼装条件实体
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        // 创建结果list接收符合条件的
        List resultList = new ArrayList<>();
        int count = 0;
        for (TbItemCat itemCat : list) {
            if (itemCat.getIsParent()) {
                CatNode catNode = new CatNode();

                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName()
                            + "</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setItem(getCatList(itemCat.getId()));
                resultList.add(catNode);
                count++;
                if (parentId == 0 && count >= 14) {
                    break;
                }
            } else {
                resultList.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName() + "");
            }
        }
        return resultList;
    }

}
