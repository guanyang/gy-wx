package org.gy.framework.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public class SystemListener extends ContextLoaderListener {

    public static ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        applicationContext = super.getCurrentWebApplicationContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

}
