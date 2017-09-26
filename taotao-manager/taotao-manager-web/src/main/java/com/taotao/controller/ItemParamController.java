package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /** 通过id查找到订单模版实例 */
    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult queryByItemcatid(@PathVariable Long itemCatId) {
        TaotaoResult result = itemParamService.findByItemcatid(itemCatId);
        return result;
    }

    /** 保存产品参数 */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult save(@PathVariable Long cid, String paramData) {// paramData参数必须跟前台的一样
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(cid);
        tbItemParam.setParamData(paramData);
        TaotaoResult result = itemParamService.saveItemParam(tbItemParam);
        return result;
    }

}
