package org.gy.framework.util.auth.token.execute;

import org.gy.framework.util.auth.token.model.TokenConfig;

public interface TokenExecutor {

    String encode(TokenConfig config);

    TokenConfig decode(String encodeString);

}
