package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientsUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    public String SSO_USER_TOKEN;

    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    public TbUser getUserByToken(String token) {
        try {
            String json = HttpClientsUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            // 将json转换成淘淘对象
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
            if (taotaoResult.getStatus() == 200) {
                TbUser tbUser = (TbUser) taotaoResult.getData();
                return tbUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
