package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientsUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    public SearchResult search(String queryString, int page) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("q", queryString);
        map.put("page", page + "");
        try {
            String json = HttpClientsUtil.doGet(SEARCH_BASE_URL, map);
            TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (result.getStatus() == 200) {
                SearchResult searchResult = (SearchResult) result.getData();
                return searchResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
