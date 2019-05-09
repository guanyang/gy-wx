package org.gy.framework.util.weixin.api.menu;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.util.GeneralResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

/**
 * 功能描述：菜单创建
 * 
 */
public class MenuCreate {

    private static final Logger LOGGER              = LoggerFactory.getLogger(MenuCreate.class);

    public static final String  MENU_CREATE_PATTERN = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";

    private MenuCreate() {
    }

    /**
     * 功能描述: 创建菜单
     * 
     * @param accessToken 公众号accessToken
     * @param menuJson 菜单数据json
     * @return
     */
    public static GeneralResponse createMenu(String accessToken,
                                             String menuJson) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(menuJson)) {
            LOGGER.error("创建菜单参数错误：accessToken={},menuJson={}", accessToken, menuJson);
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(MENU_CREATE_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.POST, menuJson, GeneralResponse.class);
    }

}
