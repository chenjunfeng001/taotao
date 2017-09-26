package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {

    /** 将商品放入到购物车 */
    public TaotaoResult getCartById(long id, Integer num, HttpServletRequest request,
            HttpServletResponse response);

    public List<CartItem> showCartItems(HttpServletRequest request, HttpServletResponse response);

    /** 购物车删除 */
    public TaotaoResult deleteCart(long id, HttpServletRequest request, HttpServletResponse response);
}
