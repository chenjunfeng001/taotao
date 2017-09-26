package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientsUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    /** 提交订单 */
    public String createOrder(Order order) {

        String json = HttpClientsUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL,
                JsonUtils.objectToJson(order));
        TaotaoResult taotaoResult = TaotaoResult.format(json);
        if (taotaoResult.getStatus() == 200) {
            Object orderId = taotaoResult.getData();
            return orderId.toString();
        }
        return "";
    }

}
