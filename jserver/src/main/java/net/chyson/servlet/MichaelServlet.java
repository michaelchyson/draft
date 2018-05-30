package net.chyson.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * michael.chyson
 * 5/30/2018
 */
public class MichaelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);

        BufferedReader in = null;
        in = req.getReader();
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            resp.getWriter().print("get parameter: " + line);
        }

    }
}
