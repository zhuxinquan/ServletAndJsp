package controller;

import model.Blah;
import model.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuxinquan on 16-8-10.
 */
public class Message extends HttpServlet{
    private String MEMBER_VIEW;

    @Override
    public void init() throws ServletException {
        MEMBER_VIEW = getServletConfig().getInitParameter("MEMBER_VIEW");
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        Blah blah = new Blah();
        blah.setUsername(username);

        String blabla = req.getParameter("blabla");
        if(blabla != null && blabla.length() != 0) {
            if(blabla.length() < 140) {
                blah.setDate(new Date());
                blah.setTxt(blabla);
                userService.addBlah(blah);
            }else {
                req.setAttribute("blabla", blabla);
            }
        }

        List<Blah> blahs = userService.getBlahs(blah);
        req.setAttribute("blahs", blahs);
        req.getRequestDispatcher(MEMBER_VIEW).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
