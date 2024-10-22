package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.model.entity.customer.Customer;
import br.edu.ifsp.arq.tsi.arqweb2.iftech.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Utils {

    public static Gson GSON = new GsonBuilder()
                                .setPrettyPrinting()
                                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                                .create();

    public static Customer getCustomer(HttpServletRequest request) {
        return (Customer) request.getSession().getAttribute("user");
    }

    public static Boolean isLoggedIn(HttpServletRequest request) {
        var session = request.getSession(false);
        return (session != null && session.getAttribute("customer") != null);
    }

    public static void writeJsonResponse(HttpServletResponse response, Map<String, Object> content) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(GSON.toJson(content));
    }
}