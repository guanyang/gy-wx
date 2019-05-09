package org.gy.framework.util.weixin.api.pay;

import org.gy.framework.util.response.Result;
import org.gy.framework.util.weixin.api.pay.model.WeixinPayCallbackRequest;

public interface WeixinPayCallbackService {

    Result processSuccessRequest(WeixinPayCallbackRequest param);

}
