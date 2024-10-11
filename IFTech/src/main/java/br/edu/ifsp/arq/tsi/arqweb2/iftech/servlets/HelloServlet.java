package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;


@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HelloServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var content = new HashMap<String, Object>();

        content.put("message", "Served at: " + request.getContextPath());
        content.put("method", request.getMethod());
        content.put("requestURI", request.getRequestURI());
        content.put("queryString", request.getQueryString());
        content.put("remoteAddr", request.getRemoteAddr());
        content.put("userAgent", request.getHeader("User-Agent"));
        content.put("parameters", request.getParameterMap());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(new Gson().toJson(content));
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
