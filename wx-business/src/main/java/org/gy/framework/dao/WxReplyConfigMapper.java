package org.gy.framework.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.gy.framework.model.WxReplyConfig;
import org.gy.framework.model.WxReplyConfigExample;

public interface WxReplyConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int countByExample(WxReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int deleteByExample(WxReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int insert(WxReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int insertSelective(WxReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    List<WxReplyConfig> selectByExample(WxReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    WxReplyConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WxReplyConfig record, @Param("example") WxReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WxReplyConfig record, @Param("example") WxReplyConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WxReplyConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_reply_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WxReplyConfig record);
}