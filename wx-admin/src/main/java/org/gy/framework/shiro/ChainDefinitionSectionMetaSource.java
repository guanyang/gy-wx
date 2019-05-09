package org.gy.framework.shiro;

import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;

/**
 * 产生责任链，确定每个url的访问权限
 * 
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {

    // 静态资源访问权限
    private String filterChainDefinitions = null;

    public Ini.Section getObject() throws Exception {
        Ini ini = new Ini();
        // 加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        // 所有资源的访问权限，必须放在最后
        /*section.put("/**", "authc");*/
        /** 如果需要一个用户只能登录一处地方,,修改为 section.put("/**", "authc,kickout,sysUser,user"); **/
        section.put("/**", "authc");
        return section;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     * 
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return false;
    }
}
