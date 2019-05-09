package org.gy.framework.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gy.framework.model.WeixinReplyConfig;
import org.gy.framework.model.WeixinReplyConfigExample;

public interface WeixinReplyConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int countByExample(WeixinReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int deleteByExample(WeixinReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int insert(WeixinReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int insertSelective(WeixinReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    List<WeixinReplyConfig> selectByExample(WeixinReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    WeixinReplyConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WeixinReplyConfig record, @Param("example") WeixinReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WeixinReplyConfig record, @Param("example") WeixinReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WeixinReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weixin_reply_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WeixinReplyConfig record);
}