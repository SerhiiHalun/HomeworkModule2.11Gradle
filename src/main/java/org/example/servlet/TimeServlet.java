package org.example.servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.EngineContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet( value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver =new FileTemplateResolver();
        resolver.setPrefix("A:\\Project Java\\HomeWorkJava\\HomeworkModule2.11Gradle\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");

        Context simpleContext = new Context(req.getLocale(), Map.of(
                "timezone", getTimezone(req,resp)));
        engine.process("timezone",simpleContext,resp.getWriter());

        resp.getWriter().close();
    }
    private String getTimezone(HttpServletRequest req,HttpServletResponse resp){
        LocalDateTime time ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String lastTimezone = null ;
        String timezoneParameter = req.getParameter("timezone");

        if(req.getParameterMap().containsKey("timezone")){

            time = LocalDateTime.now(ZoneId.of(timezoneParameter));
            resp.addCookie(new Cookie("timezone",timezoneParameter));
            return time.format(formatter) +" "+ req.getParameter("timezone");

        }

        if ( req.getHeader("Cookie") != null){
            for (Cookie cookie: req.getCookies()) {
                if ("timezone".equals(cookie.getName())) {
                    lastTimezone = cookie.getValue();
                    time = LocalDateTime.now(ZoneId.of(lastTimezone));
                    return time.format(formatter) +" "+ lastTimezone;
                }
            }
        }
        time = LocalDateTime.now();
        return time.format(formatter) + " UTC";
    }

}
