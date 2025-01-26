package ru.javatar;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;

import jakarta.servlet.http.HttpServletResponse;

public class Template{

    public void processEngine(HttpServletResponse response, IWebApplication application, String templateFile, Context context) throws ServerException, IOException{
        final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        templateResolver.setCacheable(false);
        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        PrintWriter writer = response.getWriter();
        templateEngine.process("template", context, writer);
    }
}