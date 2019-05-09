package org.gy.framework.util.auth;

import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.config.Configuration;
import org.gy.framework.util.auth.principal.AuthPrincipal;
import org.gy.framework.util.auth.storage.StorageAccessor;

final class AuthHttpServletRequestWrapper extends HttpServletRequestWrapper {

    protected static final String PRINCIPAL_ID   = "AUTH.PRINCIPAL_ID";

    private final AuthPrincipal   principal;

    private Map                   paramsSnapshot = new HashMap();

    private String                methodToShow;

    AuthHttpServletRequestWrapper(final HttpServletRequest request, final AuthPrincipal principal, Configuration configuration) {
        super(request);
        String snapshotId = request.getParameter(Constants.SNAPSHOT_ID_PARAM);
        if (StringUtils.isNotBlank(snapshotId)) {
            this.methodToShow = "POST";
            StorageAccessor storageAccessor = configuration.getStorageAccessor();
            Map paramsSnapshot = storageAccessor.getRequestParamsSnapshot(snapshotId);
            if (paramsSnapshot != null) {
                storageAccessor.removeRequestParamsSnapshot(snapshotId);
                this.paramsSnapshot = paramsSnapshot;
            }
        }
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return (principal == null || principal.isAnonymous()) ? null : principal;
    }

    @Override
    public String getRemoteUser() {
        return (principal != null && !principal.isAnonymous()) ? principal.getName() : null;
    }

    @Override
    public String getMethod() {
        if (StringUtils.isBlank(methodToShow)) {
            return super.getMethod();
        } else {
            return methodToShow;
        }
    }

    @Override
    public String getParameter(String name) {
        String strValue = super.getParameter(name);
        if (strValue != null) {
            return strValue;
        }
        Object value = this.paramsSnapshot.get(name);
        if (value == null) {
            return null;
        } else if (value instanceof String[]) {
            return ((String[]) value)[0];
        } else if (value instanceof String) {
            return (String) value;
        } else {
            return value.toString();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map getParameterMap() {
        Map result = new HashMap();
        if (!this.paramsSnapshot.isEmpty()) {
            result.putAll(this.paramsSnapshot);
        }
        result.putAll(super.getParameterMap());
        return Collections.unmodifiableMap(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(this.getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] strArray = super.getParameterValues(name);
        if (strArray != null) {
            return strArray;
        }
        Object value = this.paramsSnapshot.get(name);
        if (value == null) {
            return null;
        } else if (value instanceof String[]) {
            return (String[]) value;
        } else if (value instanceof String) {
            return new String[] {
                (String) value
            };
        } else {
            return new String[] {
                value.toString()
            };
        }
    }

    @Override
    public HttpSession getSession(boolean create) {
        invalidateSessionIfNecessary();
        HttpSession session = super.getSession(create);
        syncHttpSessionStatus(session);
        return session;
    }

    @Override
    public HttpSession getSession() {
        invalidateSessionIfNecessary();
        HttpSession session = super.getSession();
        syncHttpSessionStatus(session);
        return session;
    }

    protected void invalidateSessionIfNecessary() {
        HttpSession session = super.getSession(false);
        if (session != null) {
            // when logout, invalidate the session.
            String existedPrincipalName = (String) session.getAttribute(PRINCIPAL_ID);
            if (existedPrincipalName != null && !existedPrincipalName.equals(this.principal.getName())) {
                session.invalidate();
            }
        }
    }

    protected void syncHttpSessionStatus(HttpSession session) {
        if (session != null && !this.principal.isAnonymous()) {
            session.setAttribute(PRINCIPAL_ID, this.principal.getName());
        }
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        invalidateSessionIfNecessary();
        return super.isRequestedSessionIdValid();
    }
}
