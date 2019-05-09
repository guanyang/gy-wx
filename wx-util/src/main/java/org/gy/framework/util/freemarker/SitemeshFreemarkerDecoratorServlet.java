package org.gy.framework.util.freemarker;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import com.opensymphony.module.sitemesh.freemarker.FreemarkerDecoratorServlet;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

public class SitemeshFreemarkerDecoratorServlet extends FreemarkerDecoratorServlet {

    private static final long serialVersionUID = 6194023176811090454L;

    @Override
    protected boolean preTemplateProcess(HttpServletRequest request, HttpServletResponse response, Template template, TemplateModel templateModel) throws ServletException, IOException {
        boolean result = super.preTemplateProcess(request, response, template, templateModel);

        Configuration config = getConfiguration();

        SimpleHash hash = (SimpleHash) templateModel;

        HTMLPage htmlPage = (HTMLPage) request.getAttribute(RequestConstants.PAGE);

        String title, body, head;

        if (htmlPage == null) {
            title = "No Title";
            body = "No Body";
            head = "<!-- No head -->";
        } else {
            title = htmlPage.getTitle();

            StringWriter buffer = new StringWriter();
            htmlPage.writeBody(buffer);
            body = buffer.toString();

            buffer = new StringWriter();
            htmlPage.writeHead(buffer);
            head = buffer.toString();

            hash.put("page", htmlPage);
        }

        hash.put("title", title);
        hash.put("body", body);
        hash.put("head", head);

        if (!config.getSharedVariableNames().isEmpty()) {
            Object[] names = config.getSharedVariableNames().toArray();
            for (Object name : names) {
                hash.put(name.toString(), config.getSharedVariable(name.toString()));
            }
        }

        /*
         * Factory factory = Factory.getInstance(new
         * Config(getServletConfig())); Decorator decorator =
         * factory.getDecoratorMapper().getDecorator(request, htmlPage); ->
         * decorator.getPage()
         */

        return result;
    }

}
