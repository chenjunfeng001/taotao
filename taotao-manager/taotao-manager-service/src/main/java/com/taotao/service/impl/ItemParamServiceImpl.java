package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    /** 通过id查找到订单模版实例 */
    public TaotaoResult findByItemcatid(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    /** 保存产品参数 */
    public TaotaoResult saveItemParam(TbItemParam tbItemParam) {
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        itemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }
}
