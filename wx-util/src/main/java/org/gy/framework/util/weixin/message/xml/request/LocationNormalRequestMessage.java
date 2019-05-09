package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class LocationNormalRequestMessage extends BaseNormalRequest {

    /**
     * 地理位置维度
     */
    @XStreamAlias("Location_X")
    private Double locationX;
    /**
     * 地理位置经度
     */
    @XStreamAlias("Location_Y")
    private Double locationY;
    /**
     * 地图缩放大小
     */
    @XStreamAlias("Scale")
    private Integer scale;
    /**
     * 地理位置信息
     */
    @XStreamAlias("Label")
    private String label;

    /**
     * 获取地理位置维度
     * 
     * @return locationX 地理位置维度
     */
    public Double getLocationX() {
        return locationX;
    }

    /**
     * 设置地理位置维度
     * 
     * @param locationX 地理位置维度
     */
    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    /**
     * 获取地理位置经度
     * 
     * @return locationY 地理位置经度
     */
    public Double getLocationY() {
        return locationY;
    }

    /**
     * 设置地理位置经度
     * 
     * @param locationY 地理位置经度
     */
    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    /**
     * 获取地图缩放大小
     * 
     * @return scale 地图缩放大小
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * 设置地图缩放大小
     * 
     * @param scale 地图缩放大小
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * 获取地理位置信息
     * 
     * @return label 地理位置信息
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置地理位置信息
     * 
     * @param label 地理位置信息
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
