package org.gy.framework.util.weixin.api.ticket;

import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.api.ticket.model.JsApiTicketResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class JsApiTicketService {

    public static final String URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

    private JsApiTicketService() {
    }

    /**
     * 功能描述: 获取JSAPI票据
     * 
     * @return
     */
    public static JsApiTicketResponse getJsApiTicket(String accessToken) {
        String url = WeiXinUtil.wrapApiUrl(URL_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.GET, null, JsApiTicketResponse.class);
    }

}
