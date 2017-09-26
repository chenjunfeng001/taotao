package com.taotao.order.service;

import java.util.List;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public interface OrderService {

    /** 新增订单 */
    public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems,
            TbOrderShipping orderShipping);
}
