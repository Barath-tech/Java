package net.barath.usermanagement.web;

import net.barath.usermanagement.dao.UserDAO;
import net.barath.usermanagement.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    public UserServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getServletPath();
        switch (action){
            case "/new":
                showNewForm(request,response);
                break;
            case "/insert":
                try {
                    insertUser(request,response);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "/delete":
                try{
                    deleteUser(request,response);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "/edit":
                try {
                    showEditForm(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/update":
                try {
                    updateUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    listUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void listUser (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");//Whatever jsp file u create
        dispatcher.forward (request, response);
    }
    private void updateUser (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User (id, name, email, country);
        userDAO.updateUser (user);
        response. sendRedirect("list");
    }

    private void deleteUser (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("list");
    }
    private void showNewForm (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");//Whatever jsp file u create
        dispatcher.forward (request, response);
    }

    private void showEditForm (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");//Whatever jsp file u create
        request.setAttribute("user", existingUser);
        dispatcher.forward (request, response);
    }
    private void insertUser (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name= request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser=new User (name, email, country);
        userDAO.insertUser(newUser);
        response.sendRedirect("list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}