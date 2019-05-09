/**
 * Copyright (C), 2011-2016, org.gy.sample
 *
 * @Author gy
 * @Date 2018-01-13 17:05:13
 */
package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.biz.WxAppConfigBiz;
import org.gy.framework.bo.WxAppConfigBo;
import org.gy.framework.model.WxAppConfig;
import org.gy.framework.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 功能描述：微信通用配置Controller
 * 
 * @Date 2018-01-13 17:05:13
 */
@Controller
@RequestMapping("/wxAppConfig")
public class WxAppConfigController extends BaseController {

    private static final String RESPONSE = "response";

    @Autowired
    private WxAppConfigBiz      wxAppConfigBiz;

    /**
     * 
     * 功能描述: 跳转到列表页面
     * 
     * @return ModelAndView
     * @Date 2018-01-13 17:05:13
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("admin/wxAppConfig/WxAppConfigList.ftl");
    }

    /**
     * 功能描述: 分页查询记录
     * 
     * @param query
     * @return
     * @Date 2018-01-13 17:05:13
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView queryWxAppConfig(HttpServletRequest request,
                                         WxAppConfigBo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        initQuery(query, request);
        List<WxAppConfig> bos = wxAppConfigBiz.queryForList(query);
        Integer total = wxAppConfigBiz.queryForCount(query);
        mav.addObject("total", total);
        mav.addObject("rows", bos);
        return mav;
    }

    /**
     * 功能描述: 根据主键获取微信通用配置实体
     * 
     * @param id
     * @return
     * @Date 2018-01-13 17:05:13
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get(Long id) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<WxAppConfig> response = new Response<>();
        mav.addObject(RESPONSE, response);
        if (id == null) {
            response.setSuccess(false);
            response.setMessage("主键不能为空");
            return mav;
        }
        WxAppConfig entity = wxAppConfigBiz.selectByPrimaryKey(id);
        if (entity == null) {
            response.setSuccess(false);
            response.setMessage("实体信息不存在");
            return mav;
        }
        response.setSuccess(true);
        response.setMessage("操作成功");
        response.setResult(entity);
        return mav;
    }

    /**
     * 功能描述: 保存或更新(细节字段自行调整)
     * 
     * @param entity
     * @return
     * @Date 2018-01-13 17:05:13
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(WxAppConfig entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Long> result = new Response<>();
        mav.addObject(RESPONSE, result);
        if (entity.getId() != null && entity.getId() > 0) {
            // 更新
            try {
                entity.setUpdateTime(new Date());
                wxAppConfigBiz.updateByPrimaryKeySelective(entity);
                result.setResult(entity.getId());
            } catch (DuplicateKeyException e) {
                logger.error("更新异常：" + e.getMessage(), e);
                result.setSuccess(false);
                result.setMessage("唯一索引冲突异常");
                return mav;
            }
        } else {
            // 添加
            try {
                entity.setCreateTime(new Date());
                entity.setUpdateTime(new Date());
                Long id = wxAppConfigBiz.insertSelective(entity);
                result.setResult(id);
            } catch (DuplicateKeyException e) {
                logger.error("添加异常：" + e.getMessage(), e);
                result.setSuccess(false);
                result.setMessage("唯一索引冲突异常");
                return mav;
            }
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
     * @Date 2018-01-13 17:05:13
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView del(String ids) {
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
        Integer num = wxAppConfigBiz.deleteByPrimaryKey(list);
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功删除数：" + num);
        result.setResult(num);
        return mav;
    }

}
