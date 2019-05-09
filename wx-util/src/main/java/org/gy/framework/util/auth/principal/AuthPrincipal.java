package org.gy.framework.util.auth.principal;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

public interface AuthPrincipal extends Principal, Serializable {

    String ANONYMOUS_PRINCIPAL_ID = "_auth.anonymous";

    Map<String, String> getAttributes();

    String getAttribute(String attrName);

    boolean isAnonymous();

}
