package cn.test.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class PageHealperTest {

    @Test
    public void pageTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-*.xml");
        // 从spring容器中获得Mapper的代理对象
        TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);

        TbItemExample tbItemExample = new TbItemExample();
        PageHelper.startPage(2, 30);
        List<TbItem> list = mapper.selectByExample(tbItemExample);
        for (TbItem tb : list) {
            System.out.println(tb.getTitle());
        }
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        System.out.println("共有商品：" + total);

    }
}
