package com.taotao.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

    /** 通过id查找到订单模版实例 */
    public TaotaoResult findByItemcatid(Long itemCatId);

    /** 保存产品参数 */
    public TaotaoResult saveItemParam(TbItemParam tbItemParam);
}
