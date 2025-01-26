package ru.javatar;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.thymeleaf.context.Context;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/test")
public class TestApp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Template template = new Template();
        IWebApplication application = JakartaServletWebApplication.buildApplication(getServletContext());
        Context context = new Context();
        context.setVariable("name", "Email Validator");
        context.setVariable("path", "/test.html");
        context.setVariable("array", request.getAttribute("result"));
        template.processEngine(response, application, "template", context);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("data") != null){
            String str;
            try {
                str = request.getParameter("data");
             }
             catch (NumberFormatException e) {
                str = "";
             }
            request.setAttribute("result", email_validator(str));
        }
        doGet(request, response);
    }
    
    static String[][] email_validator(String str){
        String[] arr = str.split("\r\n");
        String result[][] = new String[arr.length][3];
        for(int i = 0; i < arr.length; i++){
            result[i][0] = arr[i].toLowerCase();
            if(regexEmail(arr[i])){
                result[i][1] = "+";
                try{
                if(DnsCheck.mxLookup(arr[i])){
                    result[i][2] = "+";
                }
                else result[i][2] = "-";
                }
                catch(Exception e){
                    result[i][2] = e.getMessage();
                }
            }
            else{
                result[i][1] = "-";
                result[i][2] = "-";
            }
        }
        return result;
    }

    static boolean regexEmail(String mail){
        //^[a-zA-Z0-9._%+-]+@[a-zA-Zа-яА-Я0-9.-]+\.[a-zA-Zа-яА-Я]{2,}$
        Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Zа-яА-Я0-9.-]+\\.[a-zA-Zа-яА-Я]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = email_pattern.matcher(mail);
        return matcher.find();
    }

}
