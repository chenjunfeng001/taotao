package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    public TaotaoResult getItemBaseInfo(long itemId) {
        // 从redis缓存中取
        try {
            String reidsJson = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            // 将json转换成pojo再放入taotaoResult中
            if (StringUtils.isNotBlank(reidsJson)) {
                TbItem tbItem = JsonUtils.jsonToPojo(reidsJson, TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        // 添加缓存逻辑
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok(item);
    }

    @Override
    public TaotaoResult getItemDesc(Long itemId) {
        // 先从缓存中取
        try {
            String getJson = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if (StringUtils.isNotBlank(getJson)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(getJson, TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 从数据库中查找
        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        // 放入缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(tbItemDesc);
    }

    public TaotaoResult getItemParam(long itemId) {
        // 添加缓存
        try {
            // 添加缓存逻辑
            // 从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            // 判断是否有值
            if (!StringUtils.isBlank(json)) {
                // 把json转换成java对象
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据商品id查询规格参数
        // 设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        // 执行查询
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem paramItem = list.get(0);
            try {
                // 把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
                // 设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(paramItem);
        }
        return TaotaoResult.build(400, "无此商品规格");
    }

}
