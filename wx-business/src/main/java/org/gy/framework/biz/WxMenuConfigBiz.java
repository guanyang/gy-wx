/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WxMenuConfigBiz.java
 *
 * @Date 2016-08-08 13:01:24
 */
package org.gy.framework.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.gy.framework.bo.WxMenuConfigBo;
import org.gy.framework.dao.WxMenuConfigMapper;
import org.gy.framework.model.WxMenuConfig;
import org.gy.framework.model.WxMenuConfigExample;
import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.menu.MenuCreate;
import org.gy.framework.util.weixin.util.GeneralResponse;
import org.gy.framework.util.weixin.util.WeiXinConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：微信菜单配置Biz
 * 
 * @Date 2016-08-08 13:01:24
 */
@Service
public class WxMenuConfigBiz extends BaseBiz {

    @Autowired
    private WeiXinBiz weiXinBiz;

    /**
     * 功能描述: 创建菜单
     * 
     * @return
     */
    public GeneralResponse createMenu(String appId) {
        // 获取token
        String token = weiXinBiz.getTokenByAppId(appId);
        return MenuCreate.createMenu(token, JacksonMapper.beanToJson(getMenu(appId)));
    }

    /**
     * 功能描述: 添加，返回主键
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public Long insert(WxMenuConfig entity) {
        sqlSessionMaster.getMapper(WxMenuConfigMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public int update(WxMenuConfig entity) {
        return sqlSessionMaster.getMapper(WxMenuConfigMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public WxMenuConfig select(Long id) {
        return sqlSessionSlave.getMapper(WxMenuConfigMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public int delete(List<Long> ids) {
        WxMenuConfigExample example = new WxMenuConfigExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(WxMenuConfigMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 获取满足条件的记录列表
     * 
     * @param query
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public List<WxMenuConfigBo> queryForList(WxMenuConfigBo query) {
        return sqlSessionSlave.selectList("WX_MENU_CONFIG.FIND_BY_PAGE", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public Integer queryForCount(WxMenuConfigBo query) {
        return sqlSessionSlave.selectOne("WX_MENU_CONFIG.COUNT_RECORD", query);
    }

    /**
     * 功能描述: 获取满足条件的菜单配置列表(不带分页)
     * 
     * @param query
     * @return
     * @Date 2016-08-08 13:01:24
     */
    public List<WxMenuConfigBo> queryMenuList(WxMenuConfigBo query) {
        return sqlSessionSlave.selectList("WX_MENU_CONFIG.SELECT_MENU_CONFIG_LIST", query);
    }

    /**
     * 功能描述: 获取微信菜单列表(微信api调用)
     * 
     * @return
     * @Author gy
     * @Date 2016年8月14日上午11:16:25
     */
    public List<WxMenuConfigBo> selectMenuList(WxMenuConfigBo query) {
        return sqlSessionSlave.selectList("WX_MENU_CONFIG.SELECT_MENU_LIST", query);
    }

    /**
     * 功能描述: 获取菜单
     * 
     * @return
     * @Author gy
     * @Date 2016年8月15日上午12:31:02
     */
    public Map<String, Object> getMenu(String appId) {
        WxMenuConfigBo query = new WxMenuConfigBo();
        query.setAppId(appId);
        List<WxMenuConfigBo> list = selectMenuList(query);
        Map<Long, WxMenuConfigBo> parentMap = new HashMap<>();
        Map<Long, List<WxMenuConfigBo>> childMap = new HashMap<>();
        for (WxMenuConfigBo bo : list) {
            if (bo.getParentId() == null || bo.getParentId() == -1) {
                parentMap.put(bo.getId(), bo);
            } else {
                if (childMap.get(bo.getParentId()) == null) {
                    List<WxMenuConfigBo> childList = new ArrayList<>();
                    childList.add(bo);
                    childMap.put(bo.getParentId(), childList);
                } else {
                    childMap.get(bo.getParentId()).add(bo);
                }
            }
        }
        List<Object> menuList = new ArrayList<>();
        for (Map.Entry<Long, WxMenuConfigBo> entry : parentMap.entrySet()) {
            WxMenuConfigBo bo = entry.getValue();
            String name = bo.getMenuName();
            if (childMap.get(entry.getKey()) == null) {
                // 没有子菜单
                Map<String, Object> parentMenuContent = new HashMap<>();
                String type = bo.getMenuType();
                parentMenuContent.put("type", type);
                parentMenuContent.put("name", name);
                if (WeiXinConstantUtil.MENU_TYPE_CLICK.equals(type)) {
                    String key = bo.getKeywords();// 改为关键字，关键字唯一
                    parentMenuContent.put("key", key);
                    menuList.add(parentMenuContent);
                } else if (WeiXinConstantUtil.MENU_TYPE_VIEW.equals(type)) {
                    String url = bo.getReplyLink();
                    parentMenuContent.put("url", url);
                    menuList.add(parentMenuContent);
                } else if (WeiXinConstantUtil.MENU_TYPE_MINIPROGRAM.equals(type)) {
                    String source = bo.getReplyText();
                    WxReplyConfigBiz.parseMiniProgramConfig(parentMenuContent, source);
                    menuList.add(parentMenuContent);
                }
            } else {
                // 有子菜单
                List<WxMenuConfigBo> subMenuList = childMap.get(entry.getKey());
                Map<String, Object> parentMenuContent = new HashMap<>();
                parentMenuContent.put("name", name);
                List<Object> subMenuContentList = new ArrayList<>();
                for (WxMenuConfigBo subMenu : subMenuList) {
                    String subMenuType = subMenu.getMenuType();
                    String subMenuname = subMenu.getMenuName();
                    Map<String, Object> subMenuContent = new HashMap<>();
                    subMenuContent.put("type", subMenuType);
                    subMenuContent.put("name", subMenuname);
                    if (WeiXinConstantUtil.MENU_TYPE_CLICK.equals(subMenuType)) {
                        String subMenuKey = subMenu.getKeywords();// 改为关键字，关键字唯一
                        subMenuContent.put("key", subMenuKey);
                        subMenuContentList.add(subMenuContent);
                    } else if (WeiXinConstantUtil.MENU_TYPE_VIEW.equals(subMenuType)) {
                        String subMenuUrl = subMenu.getReplyLink();
                        subMenuContent.put("url", subMenuUrl);
                        subMenuContentList.add(subMenuContent);
                    } else if (WeiXinConstantUtil.MENU_TYPE_MINIPROGRAM.equals(subMenuType)) {
                        String source = subMenu.getReplyText();
                        WxReplyConfigBiz.parseMiniProgramConfig(subMenuContent, source);
                        subMenuContentList.add(subMenuContent);
                    }
                }
                if (CollectionUtils.isNotEmpty(subMenuContentList)) {
                    parentMenuContent.put("sub_button", subMenuContentList);
                    menuList.add(parentMenuContent);
                }
            }
        }
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("button", menuList);
        return menuMap;
    }

}
