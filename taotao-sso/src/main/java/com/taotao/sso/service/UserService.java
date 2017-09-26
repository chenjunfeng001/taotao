package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

    public TaotaoResult checkData(String key, Integer type);

    public TaotaoResult createUser(TbUser user);

    public TaotaoResult login(String username, String password, HttpServletRequest request,
            HttpServletResponse responses);

    public TaotaoResult getUserByToken(String token);

}
