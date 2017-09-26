package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems,
            TbOrderShipping orderShipping) {
        // 从redis获取订单id封装这三个对象
        String getJson = jedisClient.get(ORDER_GEN_KEY);
        if (StringUtils.isBlank(getJson)) {
            // 如果为空给他一个初始值
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);
        tbOrder.setOrderId(orderId + "");
        // 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
        tbOrder.setStatus(1);
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        // 0：未评价 1：已评价
        tbOrder.setBuyerRate(0);
        orderMapper.insert(tbOrder);
        // 封装订单商品pojo
        for (TbOrderItem orderItem : tbOrderItems) {
            long orderItemId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            orderItem.setId(orderItemId + "");
            orderItem.setOrderId(orderId + "");
            orderItemMapper.insert(orderItem);
        }
        orderShipping.setOrderId(orderId + "");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        return TaotaoResult.ok(orderId);
    }
}
