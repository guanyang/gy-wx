package org.gy.framework.util.auth.token.execute;

import java.security.Key;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.token.model.TokenConfig;
import org.gy.framework.util.auth.util.AESUtil;
import org.gy.framework.util.json.JacksonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTokenExecutor implements TokenExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTokenExecutor.class);

    private static final Key    KEY;

    static {
        KEY = AESUtil.buildKey();
    }

    @Override
    public String encode(TokenConfig config) {
        if (config != null) {
            String json = JacksonMapper.beanToJson(config);
            LOGGER.info("TokenConfig json:" + json);
            if (StringUtils.isNotBlank(json)) {
                return AESUtil.encrypt(json, KEY);
            }
        }
        return null;
    }

    @Override
    public TokenConfig decode(String encodeString) {
        if (StringUtils.isNotBlank(encodeString)) {
            String str = AESUtil.decrypt(encodeString, KEY);
            LOGGER.info("TokenConfig json:" + str);
            if (StringUtils.isNotBlank(str)) {
                return JacksonMapper.jsonToBean(str, TokenConfig.class);
            }
        }
        return null;
    }

}
