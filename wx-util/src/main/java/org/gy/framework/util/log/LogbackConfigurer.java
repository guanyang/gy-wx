package org.gy.framework.util.log;

import java.io.FileNotFoundException;
import java.net.URL;

import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackConfigurer {
    /** Pseudo URL prefix for loading from the class path: "classpath:" */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /** Extension that indicates a log4j XML config file: ".xml" */
    public static final String XML_FILE_EXTENSION   = ".xml";

    public static void initLogging(String location) throws FileNotFoundException, JoranException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        URL url = ResourceUtils.getURL(resolvedLocation);
        if (resolvedLocation.toLowerCase().endsWith(XML_FILE_EXTENSION)) {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            joranConfigurator.doConfigure(url);
        }
    }
}
