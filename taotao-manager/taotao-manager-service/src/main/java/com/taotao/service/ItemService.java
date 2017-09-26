package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

    /** 通过ID查找物品 */
    public TbItem getTbItemById(Long id);

    /** 商品列表查询 */
    public EUDataGridResult getItemList(int page, int rows);

    /** 添加商品 */
    public TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception;

}
