/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sun.xml.wss.impl.misc.Base64;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "ControleLogin", urlPatterns = {"/ControleLogin", "/logar.html", "/logout.html"})
public class ControleLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("UseSpecificCatch")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = request.getServletPath();
			
        if(url.equalsIgnoreCase("/logar.html")) {
            
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            
            try {
                senha = Base64.encode(senha.getBytes());
                
                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());
                
                Client client = ClientBuilder.newClient();
                String id = client
                        .target("http://localhost:8080/ServidorAplicativo/webresources/autenticacao/logar")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .header("Authorization", "Basic " + encodedCredentials)
                        .post(Entity.json(encodedCredentials), String.class);

                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("senha", senha);
                session.setAttribute("id", id);

                request.getRequestDispatcher("pages/").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                String message = e.getMessage();
                if(message.equalsIgnoreCase("HTTP 401 Unauthorized")) {
                    request.setAttribute("msg", "<div class='alert alert-warning'>Não Autorizado! Email ou senha inválidos.</div>");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "<div class='alert alert-warning'>Servidor Inacessivel no momento. Por favor tente mais tarde!</div>");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
        } else if(url.equalsIgnoreCase("/logout.html")){
            
            HttpSession session = request.getSession();
            session.removeAttribute("email");
            session.removeAttribute("senha");
            session.removeAttribute("id");
            session.invalidate();

            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
