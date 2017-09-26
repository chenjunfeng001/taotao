package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String key, int page, int rows) throws Exception {
        // 创建查询对象,封装该对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(key);
        // 设置分页
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        // 设置默认搜素域
        solrQuery.set("df", "item_keywords");
        // 设置高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        SearchResult result = searchDao.search(solrQuery);
        long recordCount = result.getRecordCount();
        long pageCount = recordCount / rows;
        pageCount = recordCount % rows == 0 ? pageCount : pageCount + 1;
        result.setPageCount(pageCount);
        result.setCurPage(page);
        return result;
    }

}
