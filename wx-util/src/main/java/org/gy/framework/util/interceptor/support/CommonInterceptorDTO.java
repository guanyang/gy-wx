
package org.gy.framework.util.interceptor.support;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 功能描述：基类
 *
 */
public class CommonInterceptorDTO implements Serializable {

    private static final long serialVersionUID = 1964339128079515882L;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
