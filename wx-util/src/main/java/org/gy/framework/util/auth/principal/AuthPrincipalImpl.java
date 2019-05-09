package org.gy.framework.util.auth.principal;

import java.util.Map;

public class AuthPrincipalImpl implements AuthPrincipal {

    private static final long   serialVersionUID = 5903357195898952663L;

    /**
     * The unique identifier for this principal.
     */
    private String              name;
    /**
     * Map of key/value pairs about this principal.
     */
    private Map<String, String> attributes;

    public AuthPrincipalImpl(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public boolean isAnonymous() {
        return ANONYMOUS_PRINCIPAL_ID.equals(getName());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String getAttribute(String attrName) {
        return this.attributes.get(attrName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

}
