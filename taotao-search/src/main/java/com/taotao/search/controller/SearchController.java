package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String queryString,
            @RequestParam(defaultValue = "1") int page, //
            @RequestParam(defaultValue = "60") int rows) {
        if (StringUtils.isBlank(queryString)) {
            TaotaoResult.build(400, "查询条件不能空");
        }
        SearchResult search = null;
        try {
            // 解决乱码
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            search = searchService.search(queryString, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(search);
    }
}
