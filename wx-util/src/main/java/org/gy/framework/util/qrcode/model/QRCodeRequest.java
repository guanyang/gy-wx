/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: QRCodeRequest.java
 *
 * @Author gy
 * @Date 2016年9月16日下午11:31:06
 */
package org.gy.framework.util.qrcode.model;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 功能描述：二维码请求封装实体
 * 
 * @Author gy
 * @Date 2016年9月16日下午11:31:06
 */
public class QRCodeRequest {

    public static final int                  BLACK          = 0xFF000000;
    public static final int                  WHITE          = 0xFFFFFFFF;

    public static final int                  DEFAULT_MARGIN = 0;

    public static final int                  DEFAULT_WIDTH  = 120;

    public static final int                  DEFAULT_HEIGHT = 120;

    public static final QRCodeFormatEnum     DEFAULT_FORMAT = QRCodeFormatEnum.png;

    public static final ErrorCorrectionLevel DEFAULT_LEVEL  = ErrorCorrectionLevel.H;

    /**
     * 二维码内容
     */
    private String                           content;

    /**
     * 二维码宽度
     */
    private int                              width          = DEFAULT_WIDTH;

    /**
     * 二维码高度
     */
    private int                              height         = DEFAULT_HEIGHT;
    /**
     * 二维码输出格式，默认jpg
     */
    private QRCodeFormatEnum                 format         = DEFAULT_FORMAT;

    /**
     * 容错级别，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
     */
    private ErrorCorrectionLevel             ecLevel        = DEFAULT_LEVEL;
    /**
     * 边距像素
     */
    private int                              margin         = DEFAULT_MARGIN;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public QRCodeFormatEnum getFormat() {
        return format;
    }

    public void setFormat(QRCodeFormatEnum format) {
        this.format = format;
    }

    public ErrorCorrectionLevel getEcLevel() {
        return ecLevel;
    }

    public void setEcLevel(ErrorCorrectionLevel ecLevel) {
        this.ecLevel = ecLevel;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

}
