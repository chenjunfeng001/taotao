package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientsUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    // TODO cookie业务没弄明白
    public TaotaoResult getCartById(long id, Integer num, HttpServletRequest request,
            HttpServletResponse response) {
        List<CartItem> list = getCartItem(request);
        CartItem cartItem = null;
        // 1如果购物车有商品
        for (CartItem cItem : list) {
            if (cItem.getId() == id) {
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        // 2购物车中没有商品,新加的
        if (cartItem == null) {
            String json = HttpClientsUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + id);
            cartItem = new CartItem();
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
            if (taotaoResult.getStatus() == 200) {
                TbItem tbItem = (TbItem) taotaoResult.getData();
                cartItem.setId(tbItem.getId());
                cartItem.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(tbItem.getPrice());
                cartItem.setTitle(tbItem.getTitle());
            }
            list.add(cartItem);
        }
        // 把list中的数据转成json放到cookie中
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
        return TaotaoResult.ok();
    }

    private List<CartItem> getCartItem(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "TT_CART", true);
        if (json != null) {
            return JsonUtils.jsonToList(json, CartItem.class);
        }
        return new ArrayList<CartItem>();
    }

    /** 展示购物车中的商品 */
    public List<CartItem> showCartItems(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list = getCartItem(request);
        return list;
    }

    /** 购物车删除 */
    public TaotaoResult deleteCart(long id, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list = getCartItem(request);
        for (CartItem cartItem : list) {
            if (cartItem.getId() == id) {
                list.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
        return TaotaoResult.ok();
    }

}
