package org.gy.framework.util.weixin.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述：请求校验
 * 
 */
public class SignUtil {

    public static final boolean checkSignature(String token,
                                               String signature,
                                               String timestamp,
                                               String nonce) {
        if (StringUtils.isBlank(token) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)) {
            return false;
        }
        String[] arr = new String[] {
                token,
                timestamp,
                nonce
        };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        String tmpStr = DigestUtils.sha1Hex(content.toString());
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature) : false;

    }

}
