package org.gy.framework.util.weixin.exception;

public class WeiXinException extends RuntimeException {

    private static final long serialVersionUID = 4461403860134040260L;

    public WeiXinException() {
        super();
    }

    public WeiXinException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeiXinException(String message) {
        super(message);
    }

    public WeiXinException(Throwable cause) {
        super(cause);
    }

}
