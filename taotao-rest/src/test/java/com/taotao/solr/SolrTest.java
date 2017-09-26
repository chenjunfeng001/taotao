package com.taotao.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
    @Test
    public void addfun1() throws Exception {
        SolrServer server = new HttpSolrServer("http://192.168.2.12:8080/solr");
        // 创建document文档
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test002");
        document.addField("item_title", "测试商品2");
        document.addField("item_price", 123);
        server.add(document);
        // 提交
        server.commit();

    }

}
