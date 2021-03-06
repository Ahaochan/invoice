package moe.ahao.spring.boot.wechat.mp.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import moe.ahao.spring.boot.wechat.mp.BaseMpTest;
import moe.ahao.util.commons.io.JSONHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateMenuTest extends BaseMpTest {

    /**
     * 通过 JSON 创建菜单
     * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013">自定义菜单创建接口</a>
     */
    @ParameterizedTest
    @ValueSource(strings = {"{\"button\":[{\"type\":\"view\",\"name\":\"百度\",\"url\":\"http://www.baidu.com/\"}]}"})
    void createMenuByJson(String json) {
        Assumptions.assumeTrue(StringUtils.isNotBlank(json), "需要配置菜单JSON");
        try {
            String responseJson = menuService.menuCreate(json);
            Assertions.assertNull(responseJson);
        } catch (WxErrorException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    /**
     * 通过 JSON 创建菜单
     * 在 {@link #createMenuByJson(String json)} 的基础上添加 matchrule 字段, 匹配特定的用户群体
     * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296">创建个性化菜单</a>
     */
    @Test
    void createConditionalMenuByJson() {
        // 创建个性化菜单: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296
        Map<String, String> json = new HashMap<>();
        json.put("matchrule", "");
        try {
            String responseJson = menuService.menuCreate(JSONHelper.toString(json));

            Map<String, String> response = JSONHelper.parse(responseJson, new TypeReference<Map<String, String>>() {});
            Assertions.assertAll("请求错误",
                () -> Assertions.assertEquals("0", response.get("errcode")),
                () -> Assertions.assertEquals("ok", response.get("errmsg"))
            );
        } catch (WxErrorException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    private WxMenu getMenu() {
        WxMenu menu = new WxMenu();

        // 1. 第一个一级菜单
        WxMenuButton btn1 = new WxMenuButton();
        btn1.setName("父菜单");
        {
            List<WxMenuButton> btnList = btn1.getSubButtons();

            // 点击推事件用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
            WxMenuButton subBtn1 = new WxMenuButton();
            subBtn1.setType(WxConsts.MenuButtonType.CLICK);
            subBtn1.setName("赞一下我们");
            subBtn1.setKey("回调key");

            WxMenuButton subBtn3 = new WxMenuButton();
            subBtn3.setType(WxConsts.MenuButtonType.VIEW);
            subBtn3.setName("搜索");
            subBtn3.setUrl("http://www.soso.com/");

            WxMenuButton subBtn2 = new WxMenuButton();
            subBtn2.setType(WxConsts.MenuButtonType.VIEW);
            subBtn2.setName("视频");
            subBtn2.setUrl("http://v.qq.com/");



        }



        btn1.setType(WxConsts.MenuButtonType.CLICK);
        btn1.setKey("V1001_TODAY_MUSIC");

//    WxMenuButton button2 = new WxMenuButton();
//    button2.setType(WxConsts.MenuButtonType.MINIPROGRAM);
//    button2.setName("小程序");
//    button2.setAppId("wx286b93c14bbf93aa");
//    button2.setPagePath("pages/lunar/index.html");
//    button2.setUrl("http://mp.weixin.qq.com");

        WxMenuButton button3 = new WxMenuButton();
        button3.setName("菜单");

        menu.getButtons().add(btn1);
//    menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        WxMenuButton button31 = new WxMenuButton();
        button31.setType(WxConsts.MenuButtonType.VIEW);
        button31.setName("搜索");
        button31.setUrl("http://www.soso.com/");

        WxMenuButton button32 = new WxMenuButton();
        button32.setType(WxConsts.MenuButtonType.VIEW);
        button32.setName("视频");
        button32.setUrl("http://v.qq.com/");

        WxMenuButton button33 = new WxMenuButton();
        button33.setType(WxConsts.MenuButtonType.CLICK);
        button33.setName("赞一下我们");
        button33.setKey("V1001_GOOD");

        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        button3.getSubButtons().add(button33);

        return menu;
    }
}
