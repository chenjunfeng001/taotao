package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品管理service
 * 
 * @author Administrator
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getTbItemById(Long id) {
        // TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        TbItemExample itemExample = new TbItemExample();
        Criteria criteria = itemExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> list = tbItemMapper.selectByExample(itemExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /** 商品列表查询 */
    public EUDataGridResult getItemList(int page, int rows) {
        TbItemExample tbItemExample = new TbItemExample();
        EUDataGridResult result = new EUDataGridResult();
        // 分页处理 ,原理是拦截器一样,在dao方法执行前先加入条件
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        result.setRows(list);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * 添加商品
     * 
     * @throws Exception
     */
    public TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception {
        // 将tbitem填充
        // 商品id和状态码
        Long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItemMapper.insert(tbItem);
        // 同时添加商品描述
        TaotaoResult insertItemDesc = insertItemDesc(id, desc);
        if (insertItemDesc.getStatus() != 200) {
            throw new Exception("添加商品描述失败");
        }
        insertItemDesc = insertItemParamItem(id, itemParams);
        if (insertItemDesc.getStatus() != 200) {
            throw new Exception("添加商品描述失败");
        }
        return TaotaoResult.ok();
    }

    /** 添加商品的同时跟新信息描述 */
    private TaotaoResult insertItemDesc(Long id, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /** 添加商品的同时添加订单模版信息itemParamItemMapper */
    private TaotaoResult insertItemParamItem(Long id, String itemParams) {
        TbItemParamItem item = new TbItemParamItem();
        item.setItemId(id);
        item.setParamData(itemParams);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemParamItemMapper.insert(item);
        return TaotaoResult.ok();
    }
}
