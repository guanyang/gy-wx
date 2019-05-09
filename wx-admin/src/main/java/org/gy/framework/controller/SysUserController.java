/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-admin
 * FileName: SysUserController.java
 *
 * @Date 2016-08-05 22:41:16
 */
package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.biz.SysUserBiz;
import org.gy.framework.bo.SysUserBo;
import org.gy.framework.model.SysUser;
import org.gy.framework.util.PasswordHelper;
import org.gy.framework.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 功能描述：系统管理用户记录Controller
 * 
 * @Date 2016-08-05 22:41:16
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    private static final String RESPONSE         = "response";

    private static final String DEFAULT_PASSWORD = "guanyang123";

    @Autowired
    private SysUserBiz          sysUserBiz;

    /**
     * 
     * 功能描述: 跳转到列表页面
     * 
     * @return ModelAndView
     * @Date 2016-08-05 22:41:16
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/sysUser/sysUserList.ftl");
        mav.addObject("defaultPassword", DEFAULT_PASSWORD);
        return mav;
    }

    /**
     * 功能描述: 分页查询记录
     * 
     * @param query
     * @return
     * @Date 2016-08-05 22:41:16
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView querySysUser(HttpServletRequest request,
                                     SysUserBo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        initQuery(query, request);
        List<SysUser> bos = sysUserBiz.queryForList(query);
        Integer total = sysUserBiz.queryForCount(query);
        mav.addObject("total", total);
        mav.addObject("rows", bos);
        return mav;
    }

    /**
     * 功能描述: 根据主键获取系统管理用户记录实体
     * 
     * @param id
     * @return
     * @Date 2016-08-05 22:41:16
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get(Long id) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<SysUser> response = new Response<SysUser>();
        mav.addObject(RESPONSE, response);
        if (id == null) {
            response.setSuccess(false);
            response.setMessage("主键不能为空");
            return mav;
        }
        SysUser entity = sysUserBiz.selectByPrimaryKey(id);
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
     * @Date 2016-08-05 22:41:16
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(SysUser entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Long> result = new Response<Long>();
        mav.addObject(RESPONSE, result);
        if (entity.getId() != null && entity.getId() > 0) {
            // 更新
            try {
                sysUserBiz.updateByPrimaryKeySelective(entity);
                result.setResult(entity.getId());
            } catch (DuplicateKeyException e) {
                logger.error("更新异常：" + e.getMessage(), e);
                result.setSuccess(false);
                result.setMessage("账户" + entity.getAccountName() + "已存在");
                return mav;
            }
        } else {
            String salt = PasswordHelper.generateSalt();// 盐值
            String password = PasswordHelper.generatePassword(entity.getPassword(), salt);// 根据明文生成密文密码
            entity.setSalt(salt);
            entity.setPassword(password);
            try {
                Long id = sysUserBiz.insertSelective(entity);
                result.setResult(id);
            } catch (DuplicateKeyException e) {
                logger.error("添加异常：" + e.getMessage(), e);
                result.setSuccess(false);
                result.setMessage("账户" + entity.getAccountName() + "已存在");
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
     * @Date 2016-08-05 22:41:16
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView del(String ids) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Integer> result = new Response<Integer>();
        result.setSuccess(false);
        mav.addObject(RESPONSE, result);
        if (StringUtils.isBlank(ids)) {
            result.setMessage("请选择要删除的数据");
            return mav;
        }
        String[] idArr = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String id : idArr) {
            list.add(Long.valueOf(id));
        }
        Integer num = sysUserBiz.deleteByPrimaryKey(list);
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功删除数：" + num);
        result.setResult(num);
        return mav;
    }

    /**
     * 功能描述: 批量重置密码
     * 
     * @param ids
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView reset(String ids) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Integer> result = new Response<Integer>();
        result.setSuccess(false);
        mav.addObject(RESPONSE, result);
        if (StringUtils.isBlank(ids)) {
            result.setMessage("请选择要处理的数据");
            return mav;
        }
        String[] idArr = ids.split(",");
        int num = 0;
        SysUser entity;
        for (String id : idArr) {
            entity = new SysUser();
            String salt = PasswordHelper.generateSalt();// 盐值
            String password = PasswordHelper.generatePassword(DEFAULT_PASSWORD, salt);// 重置成默认密码
            entity.setSalt(salt);
            entity.setPassword(password);
            entity.setId(Long.valueOf(id));
            int tmp = sysUserBiz.updateByPrimaryKeySelective(entity);
            if (tmp > 0) {
                num++;
            }
        }
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功数：" + num);
        result.setResult(num);
        return mav;
    }

}
