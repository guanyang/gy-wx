/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-admin
 * FileName: WeixinUserRecordController.java
 *
 * @Date 2016-08-08 00:00:59
 */
package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.biz.WeiXinBiz;
import org.gy.framework.biz.WxAppConfigBiz;
import org.gy.framework.biz.WxUserRecordBiz;
import org.gy.framework.bo.WxUserRecordBo;
import org.gy.framework.model.WxUserRecord;
import org.gy.framework.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 功能描述：微信用户记录Controller
 * 
 * @Date 2016-08-08 00:00:59
 */
@Controller
@RequestMapping("/weixinUserRecord")
public class WeixinUserRecordController extends BaseController {

    private static final String SUCCESS  = "操作成功";
    private static final String RESPONSE = "response";
    @Autowired
    private WxUserRecordBiz     wxUserRecordBiz;

    @Autowired
    private WeiXinBiz           weiXinBiz;

    @Autowired
    private WxAppConfigBiz      wxAppConfigBiz;

    /**
     * 
     * 功能描述: 跳转到列表页面
     * 
     * @return ModelAndView
     * @Date 2016-08-08 00:00:59
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        Map<String, String> map = wxAppConfigBiz.getAllWxAppConfig(-1);
        mav.addObject("wxType", map);
        mav.setViewName("admin/weixinUserRecord/weixinUserRecordList.ftl");
        return mav;
    }

    /**
     * 功能描述: 分页查询记录
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:00:59
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView queryWeixinUserRecord(WxUserRecordBo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        query.setIndex((query.getPage() - 1) * query.getRows());
        List<WxUserRecordBo> list = new ArrayList<>();
        int total = wxUserRecordBiz.queryForCount(query);
        if (total > 0) {
            list = wxUserRecordBiz.queryForList(query);
        }
        mav.addObject("rows", list);
        mav.addObject("total", total);
        return mav;
    }

    /**
     * 功能描述: 根据主键获取微信用户记录实体
     * 
     * @param id
     * @return
     * @Date 2016-08-08 00:00:59
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
        WxUserRecord entity = wxUserRecordBiz.select(id);
        if (entity == null) {
            response.setSuccess(false);
            response.setMessage("实体信息不存在");
            return mav;
        }
        response.setResult(entity);
        response.setSuccess(true);
        response.setMessage(SUCCESS);
        return mav;
    }

    /**
     * 功能描述: 保存或更新(细节字段自行调整)
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:00:59
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(WxUserRecord entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Long> result = new Response<>();
        mav.addObject(RESPONSE, result);
        if (entity.getId() != null && entity.getId() > 0) {
            // 更新
            entity.setUpdateTime(new Date());
            wxUserRecordBiz.update(entity);
            result.setResult(entity.getId());
        } else {
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            Long id = wxUserRecordBiz.insert(entity);
            result.setResult(id);
        }
        result.setSuccess(true);
        result.setMessage(SUCCESS);
        return mav;
    }

    /**
     * 功能描述: 根据主键批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 00:00:59
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
        num = wxUserRecordBiz.delete(list);
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功删除数：" + num);
        result.setResult(num);
        return mav;
    }

    /**
     * 功能描述: 更新用户信息
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 00:00:59
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.GET)
    public ModelAndView updateUserInfo(String openIds,
                                       String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Integer> result = new Response<>();
        result.setSuccess(false);
        mav.addObject(RESPONSE, result);
        if (StringUtils.isBlank(appId)) {
            result.setMessage("请选择要操作的公众号");
            return mav;
        }
        if (StringUtils.isBlank(openIds)) {
            result.setMessage("请选择要操作的数据");
            return mav;
        }
        String[] idArr = openIds.split(",");
        if (idArr.length > 20) {
            result.setMessage("操作的数据不能超过20条");
            return mav;
        }
        for (String id : idArr) {
            weiXinBiz.addWxUserInfo(id, appId);
        }
        result.setSuccess(true);
        result.setMessage("用户信息异步处理中，请稍后查看");
        return mav;
    }

    @RequestMapping(value = "/accessToken", method = RequestMethod.GET)
    public ModelAndView accessToken(String appId) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<String> response = new Response<>();
        mav.addObject(RESPONSE, response);
        try {
            String token = weiXinBiz.getTokenByAppId(appId);
            response.setSuccess(true);
            response.setMessage(SUCCESS);
            response.setResult(token);
        } catch (Exception e) {
            logger.error("获取token异常：" + e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return mav;
    }

}
