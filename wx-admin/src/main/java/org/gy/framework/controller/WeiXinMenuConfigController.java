/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName: cpx-admin
 * FileName: WeixinMenuConfigController.java
 *
 * @Date 2016-08-08 13:01:25
 */
package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.biz.WxAppConfigBiz;
import org.gy.framework.biz.WxMenuConfigBiz;
import org.gy.framework.bo.WxMenuConfigBo;
import org.gy.framework.model.WxMenuConfig;
import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.response.Response;
import org.gy.framework.util.weixin.util.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 功能描述：微信菜单配置Controller
 * 
 * @Date 2016-08-08 13:01:25
 */
@Controller
@RequestMapping("/weixinMenuConfig")
public class WeiXinMenuConfigController extends BaseController {

    private static final String RESPONSE = "response";

    @Autowired
    private WxMenuConfigBiz     wxMenuConfigBiz;

    @Autowired
    private WxAppConfigBiz      wxAppConfigBiz;

    /**
     * 
     * 功能描述: 跳转到列表页面
     * 
     * @return ModelAndView
     * @Date 2016-08-08 13:01:25
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        Map<String, String> map = wxAppConfigBiz.getAllWxAppConfig(-1);
        mav.addObject("wxType", map);
        mav.setViewName("admin/weixinMenuConfig/weixinMenuConfigList.ftl");
        return mav;
    }

    /**
     * 功能描述: 分页查询记录
     * 
     * @param query
     * @return
     * @Date 2016-08-08 13:01:25
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView queryWeixinMenuConfig(WxMenuConfigBo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        query.setIndex((query.getPage() - 1) * query.getRows());
        int total;
        List<WxMenuConfigBo> list = new ArrayList<>();
        total = wxMenuConfigBiz.queryForCount(query);
        if (total > 0) {
            list = wxMenuConfigBiz.queryForList(query);
        }
        mav.addObject("total", total);
        mav.addObject("rows", list);
        return mav;
    }

    /**
     * 功能描述: 根据主键获取微信菜单配置实体
     * 
     * @param id
     * @return
     * @Date 2016-08-08 13:01:25
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get(Long id,
                            String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Object> response = new Response<>();
        mav.addObject(RESPONSE, response);
        if (id == null) {
            response.setSuccess(false);
            response.setMessage("主键不能为空");
            return mav;
        }
        WxMenuConfig entity = wxMenuConfigBiz.select(id);
        if (entity == null) {
            response.setSuccess(false);
            response.setMessage("实体信息不存在");
            return mav;
        }
        response.setResult(entity);
        response.setSuccess(true);
        response.setMessage("操作成功");
        return mav;
    }

    /**
     * 功能描述: 保存或更新(细节字段自行调整)
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 13:01:25
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(WxMenuConfig entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Long> result = new Response<>();
        mav.addObject(RESPONSE, result);
        if (entity.getId() != null && entity.getId() > 0) {
            // 更新
            entity.setUpdateTime(new Date());
            wxMenuConfigBiz.update(entity);
            result.setResult(entity.getId());
        } else {
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            Long id = wxMenuConfigBiz.insert(entity);
            result.setResult(id);
        }
        result.setSuccess(true);
        result.setMessage("操作成功");
        return mav;
    }

    /**
     * 功能描述: 根据主键批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 13:01:25
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView del(String ids,
                            String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Integer> result = new Response<>();
        result.setSuccess(false);
        mav.addObject(RESPONSE, result);
        if (StringUtils.isBlank(ids)) {
            result.setMessage("请选择要删除的数据");
            return mav;
        }
        String[] idArr = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String id : idArr) {
            list.add(Long.valueOf(id));
        }
        int num;
        num = wxMenuConfigBiz.delete(list);
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功删除数：" + num);
        result.setResult(num);
        return mav;
    }

    /**
     * 功能描述: 创建微信菜单
     * 
     * @return
     * @Author gy
     * @Date 2016年8月15日上午12:33:02
     */
    @RequestMapping(value = "/createMenu", method = RequestMethod.GET)
    public ModelAndView createMenu(String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response result = new Response();
        mav.addObject(RESPONSE, result);
        GeneralResponse generalResponse = wxMenuConfigBiz.createMenu(appId);
        if (generalResponse == null) {
            result.setSuccess(false);
            result.setMessage("创建微信菜单异常");
            return mav;
        }
        result.setSuccess(generalResponse.isSuccess());
        result.setMessage(JacksonMapper.beanToJson(generalResponse));
        return mav;
    }

    @RequestMapping(value = "/getMenu", method = RequestMethod.GET)
    public ModelAndView getMenu(String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject(wxMenuConfigBiz.getMenu(appId));
        return mav;
    }
}