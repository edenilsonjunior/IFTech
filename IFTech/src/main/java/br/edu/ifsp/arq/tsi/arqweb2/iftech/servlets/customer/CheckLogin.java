package br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.customer;

import br.edu.ifsp.arq.tsi.arqweb2.iftech.servlets.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;


@WebServlet("/checkLogin")
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckLogin() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var isLoggedIn = Utils.isLoggedIn(request);
        var content = new HashMap<String, Object>();
        content.put("loggedIn", isLoggedIn);

        Utils.writeJsonResponse(response, content);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
