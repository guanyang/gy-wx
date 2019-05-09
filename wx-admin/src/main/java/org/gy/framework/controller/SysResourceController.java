/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-admin
 * FileName: SysResourceController.java
 *
 * @Author gy
 * @Date 2016年7月12日下午10:21:00
 */
package org.gy.framework.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.biz.SysResourceBiz;
import org.gy.framework.bo.SysResourceQueryBo;
import org.gy.framework.model.SysResource;
import org.gy.framework.util.TreeNode;
import org.gy.framework.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 功能描述：系统资源配置controller
 * 
 * @Author gy
 * @Date 2016年7月12日下午10:21:00
 */
@Controller
@RequestMapping("/sysResource")
public class SysResourceController extends BaseController {

    @Autowired
    private SysResourceBiz sysResourceBiz;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("admin/sysResource/index.ftl");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request,
                             SysResourceQueryBo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        initQuery(query, request);
        List<SysResource> list = sysResourceBiz.queryForList(query);// 所有数据
        Integer total = sysResourceBiz.queryForCount(query);
        mav.addObject("rows", list);
        mav.addObject("total", total);
        return mav;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public Object menu(SysResourceQueryBo query) {
        List<SysResource> list = sysResourceBiz.getAllResources(query);// 所有数据
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if (list != null && list.size() > 0) {
            List<SysResource> firstList = getAllFirst(list);// 所有一级节点数据
            for (SysResource res : firstList) {
                TreeNode node = getTreeNodeBySysResource(list, res);
                treeNodes.add(node);
            }
        }
        return treeNodes;
    }

    /**
     * 功能描述: 添加或更新
     * 
     * @param entity
     * @return
     * @Author gy
     * @Date 2016年7月16日下午4:24:41
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveAndUpdate(SysResource entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Long> result = new Response<Long>();
        result.setSuccess(false);
        mav.addObject("response", result);
        if (entity == null || StringUtils.isBlank(entity.getName()) || entity.getParentId() == null || entity.getStatus() == null) {
            result.setMessage("配置信息不完整");
            return mav;
        }
        if (entity.getId() != null && entity.getId() > 0) {
            // 更新
            sysResourceBiz.updateByPrimaryKeySelective(entity);
            result.setResult(entity.getId());
        } else {
            Long id = sysResourceBiz.insertSelective(entity);
            result.setResult(id);
        }
        result.setSuccess(true);
        result.setMessage("操作成功");
        return mav;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ModelAndView del(String ids) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<Integer> result = new Response<Integer>();
        result.setSuccess(false);
        mav.addObject("response", result);
        if (StringUtils.isBlank(ids)) {
            result.setMessage("请选择要删除的数据");
            return mav;
        }
        String[] idArr = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String id : idArr) {
            list.add(Long.valueOf(id));
        }
        Integer num = sysResourceBiz.deleteByPrimaryKey(list);
        result.setSuccess(true);
        result.setMessage("总节点数：" + idArr.length + "，成功删除节点数：" + num);
        result.setResult(num);
        return mav;
    }

    /**
     * 功能描述: 递归调用，获取树节点
     * 
     * @param source
     * @param res
     * @return
     * @Author gy
     * @Date 2016年7月12日下午11:30:09
     */
    private TreeNode getTreeNodeBySysResource(List<SysResource> source,
                                              SysResource res) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(res.getId().toString());
        treeNode.setText(res.getName());
        treeNode.setUrl(res.getUrl());
        treeNode.setDescription(res.getDescription());
        treeNode.setParentId(res.getParentId());
        treeNode.setStatus(res.getStatus());
        // 获取子节点
        List<SysResource> childrenList = getChildList(source, res.getId());
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (SysResource entity : childrenList) {
            TreeNode tn = getTreeNodeBySysResource(source, entity); // 循环子节点,得到子节点下的孙节点集合,调用本身,可一直向下递归
            treeNodes.add(tn);
        }
        if (treeNodes.size() > 0) {
            treeNode.setChildren(treeNodes); // 添加封装好数据的子节点集合
        }
        return treeNode;
    }

    /**
     * 功能描述: 获取指定节点的所有子节点
     * 
     * @param source
     * @param id
     * @return
     * @Author gy
     * @Date 2016年7月12日下午11:20:59
     */
    private List<SysResource> getChildList(List<SysResource> source,
                                           Long id) {
        List<SysResource> list = new ArrayList<SysResource>();
        for (SysResource res : source) {
            if (res.getParentId().longValue() == id) {
                list.add(res);
            }
        }
        return list;
    }

    /**
     * 功能描述: 获取所有一级节点
     * 
     * @param source
     * @return
     * @Author gy
     * @Date 2016年7月12日下午11:40:16
     */
    private List<SysResource> getAllFirst(List<SysResource> source) {
        List<SysResource> list = new ArrayList<SysResource>();
        for (SysResource res : source) {
            if (!checkParentExist(source, res)) {
                list.add(res);
            }
        }
        return list;
    }

    /**
     * 功能描述: 验证指定节点是否有父节点，true有，false没有
     * 
     * @param source
     * @param id
     * @return
     * @Author gy
     * @Date 2016年7月14日上午2:40:53
     */
    private boolean checkParentExist(List<SysResource> source,
                                     SysResource res) {
        for (SysResource entity : source) {
            if (entity.getId().longValue() == res.getParentId()) {
                return true;
            }
        }
        return false;
    }

}
