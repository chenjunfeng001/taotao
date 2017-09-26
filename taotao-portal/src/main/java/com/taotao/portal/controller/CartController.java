package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable("itemId") long itemId,
            @RequestParam(value = "num", defaultValue = "1") Integer num, HttpServletRequest request,
            HttpServletResponse response) {
        TaotaoResult taotaoResult = cartService.getCartById(itemId, num, request, response);
        // 没解决重复刷新,提交订单的问题
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCartItems(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> list = cartService.showCartItems(request, response);
        model.addAttribute("cartList", list);
        return "cart";
    }

    /** 将购物车中的商品移除 */
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable("itemId") long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        cartService.deleteCart(itemId, request, response);
        return "redirect:/cart/cart.html";
    }

}
