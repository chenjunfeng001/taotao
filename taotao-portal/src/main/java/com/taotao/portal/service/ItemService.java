package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {

    public ItemInfo getItemById(long id);

    public String getDescById(long id);

    public String getItemParam(long itemId);
}
