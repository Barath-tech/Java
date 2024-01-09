package me.barath.compound_calculator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Calculator_Servlet" , urlPatterns = {"/",""})
public class Calculator_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String principleAmount=request.getParameter("principleamt");
        String interestRate=request.getParameter("interestrate");
        String years=request.getParameter("years");
        String compoundingperiod=request.getParameter("compoundingperiod");

        String error;

        if(principleAmount == null ||  interestRate == null  || years== null ||  compoundingperiod==null ){
            error="One or more of the input boxes is Blank. Try again!!";

            request.setAttribute("error",error);
        }else{
            double result=Utils.compound_interest(Double.parseDouble(principleAmount),(Double.parseDouble(interestRate)/100),Double.parseDouble(years),Double.parseDouble(compoundingperiod));
            request.setAttribute("result",result);
            request.setAttribute("principleamt",principleAmount);
            request.setAttribute("interest",interestRate);
            request.setAttribute("years",years);
            request.setAttribute("compoundingperiod",compoundingperiod);
        }


        getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }
}