package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientsUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;

    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

    public ItemInfo getItemById(long id) {
        try {
            String getJson = HttpClientsUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + id);
            if (StringUtils.isNotBlank(getJson)) {
                // 将json转换成对象
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(getJson, ItemInfo.class);
                if (taotaoResult.getStatus() == 200) {
                    ItemInfo itemInfo = (ItemInfo) taotaoResult.getData();
                    return itemInfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDescById(long id) {
        try {
            String getJson = HttpClientsUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + id);
            if (StringUtils.isNotBlank(getJson)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(getJson, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemDesc tbItemDesc = (TbItemDesc) taotaoResult.getData();
                    return tbItemDesc.getItemDesc();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getItemParam(long itemId) {
        try {
            String getJson = HttpClientsUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
            // 把json转换成java对象
            TaotaoResult result = TaotaoResult.formatToPojo(getJson, TbItemParamItem.class);
            if (result.getStatus() == 200) {
                TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
                String paramData = itemParamItem.getParamData();
                // 生成html
                // 把规格参数json数据转换成java对象
                List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                StringBuffer sb = new StringBuffer();
                sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                sb.append("    <tbody>\n");
                for (Map m1 : jsonList) {
                    sb.append("        <tr>\n");
                    sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group")
                            + "</th>\n");
                    sb.append("        </tr>\n");
                    List<Map> list2 = (List<Map>) m1.get("params");
                    for (Map m2 : list2) {
                        sb.append("        <tr>\n");
                        sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                        sb.append("            <td>" + m2.get("v") + "</td>\n");
                        sb.append("        </tr>\n");
                    }
                }
                sb.append("    </tbody>\n");
                sb.append("</table>");
                // 返回html片段
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
