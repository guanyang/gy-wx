/**
* Copyright (C), 2011-2016, org.gy.sample
*
* @Author gy
* @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
package ${entity.javaPackage};

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import ${entity.rootPackage}.biz.${entity.className}Biz;
import ${entity.rootPackage}.bo.${entity.className}Bo;
import ${entity.rootPackage}.model.${entity.className};
import ${entity.rootPackage}.util.response.Response;

/**
 * 功能描述：${entity.tableComment}Controller
 * 
 * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Controller
@RequestMapping("/${entity.lowerClassName}")
public class ${entity.className}Controller extends BaseController{

	private static final String RESPONSE = "response";
    
    @Autowired
    private ${entity.className}Biz           ${entity.lowerClassName}Biz;
    
    <#list entity.properties as property>
    <#if property.pk>
    <#assign primary=property >
    <#break>
    </#if>
    </#list>
    
    /**
     * 
     * 功能描述: 跳转到列表页面
     * 
     * @return ModelAndView
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    @RequestMapping(value ="/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("admin/${entity.lowerClassName}/${entity.className}List.ftl");
    }

    /**
     * 功能描述: 分页查询记录
     *
     * @param query
     * @return
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query${entity.className}(HttpServletRequest request,
                                            ${entity.className}Bo query) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        initQuery(query, request);
        List<${entity.className}> bos =  ${entity.lowerClassName}Biz.queryForList(query);
        Integer total =${entity.lowerClassName}Biz.queryForCount(query);
        mav.addObject("total", total);
        mav.addObject("rows",bos);
        return mav;
    }
    
    /**
     * 功能描述: 根据主键获取${entity.tableComment}实体
     *
     * @param id
     * @return
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ModelAndView get(${primary.javaType} id) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<${entity.className}> response = new Response<${entity.className}>();
        mav.addObject(RESPONSE, response);
        if (id == null) {
            response.setSuccess(false);
            response.setMessage("主键不能为空");
            return mav;
        }
        ${entity.className} entity = ${entity.lowerClassName}Biz.selectByPrimaryKey(id);
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
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(${entity.className} entity) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response<${primary.javaType}> result = new Response<${primary.javaType}>();
        mav.addObject(RESPONSE, result);
        if (entity.get${primary.propertyName?cap_first}() != null && entity.get${primary.propertyName?cap_first}() > 0) {
        	// 更新
            try {
            	//entity.setUpdateTime(new Date());
                ${entity.lowerClassName}Biz.updateByPrimaryKeySelective(entity);
                result.setResult(entity.get${primary.propertyName?cap_first}());
            } catch (DuplicateKeyException e) {
                logger.error("更新异常：" + e.getMessage(), e);
                result.setSuccess(false);
                result.setMessage("唯一索引冲突异常");
                return mav;
            }
        } else {
        	// 添加
            try {
            	//entity.setCreateTime(new Date());
            	//entity.setUpdateTime(new Date());
                ${primary.javaType} id = ${entity.lowerClassName}Biz.insertSelective(entity);
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
     * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
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
        List<${primary.javaType}> list = new ArrayList<${primary.javaType}>();
        for (String id : idArr) {
            list.add(${primary.javaType}.valueOf(id));
        }
        Integer num = ${entity.lowerClassName}Biz.deleteByPrimaryKey(list);
        result.setSuccess(true);
        result.setMessage("总数：" + idArr.length + "，成功删除数：" + num);
        result.setResult(num);
        return mav;
    }    
    
    
    
}
