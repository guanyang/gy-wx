package org.gy.framework.util.weixin.message.xml.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：音乐消息
 * 
 */
@XStreamAlias("xml")
public class MusicResponseMessage extends WeiXinResponse {

    @XStreamAlias("Music")
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

}
