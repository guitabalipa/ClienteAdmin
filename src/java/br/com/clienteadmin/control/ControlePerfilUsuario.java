/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import br.com.clienteadmin.model.Pessoa;
import com.google.gson.Gson;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "ControlePerfilUsuario", urlPatterns = {"/ControlePerfilUsuario", "/detalhesperfil.html", "/editarperfil.html", "/mudarsenha.html"})
public class ControlePerfilUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        Gson gson = new Gson();
        String url = request.getServletPath();
			
        if(url.equalsIgnoreCase("/detalhesperfil.html")) {
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                Integer id = new Integer(String.valueOf(session.getAttribute("id")));
                
                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/pessoa/buscarPerfilUsuario/"+id)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);

                Pessoa pessoa = gson.fromJson(resp, Pessoa.class);
                
//                PrintWriter out = response.getWriter();
//                out.println(resp);
                
                request.setAttribute("pessoa", pessoa);
                request.getRequestDispatcher("pages/detalhesperfilusuario.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/editarperfil.html")) {
            
            String idpessoa = request.getParameter("idpessoa");
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email1 = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            
            Pessoa p = new Pessoa();
            p.setPessoaid(new Integer(idpessoa));
            p.setNome(nome);
            p.setSobrenome(sobrenome);
            p.setLogin(email1);
            p.setCpf(cpf);
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                
                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                client
                    .target("http://localhost:8080/ServidorAplicativo/webresources/pessoa/atualizarPessoa")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Basic " + encodedCredentials)
                    .post(Entity.json(p), String.class);

//                Pessoa pessoa = gson.fromJson(resp, Pessoa.class);
                
//                PrintWriter out = response.getWriter();
//                out.println(resp);
                
                request.setAttribute("msg", "<div class='alert alert-info'>Alterado com sucesso!</div>");
                request.setAttribute("pessoa", p);
                request.getRequestDispatcher("pages/detalhesperfilusuario.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/mudarsenha.html")) {
            
            String senha1 = request.getParameter("senha1");
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                String id = String.valueOf(session.getAttribute("id"));
                
                Pessoa p = new Pessoa();
                senha1 = Base64.encode(senha1.getBytes());
                p.setSenha(senha1);
                p.setPessoaid(new Integer(id));
                
                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/pessoa/atualizarSenha")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(p), String.class);

                Pessoa pessoa = gson.fromJson(resp, Pessoa.class);
                
//                PrintWriter out = response.getWriter();
//                out.println(resp);
                
                request.setAttribute("msg", "<div class='alert alert-info'>Alterado com sucesso!</div>");
                request.setAttribute("pessoa", pessoa);
                request.getRequestDispatcher("pages/detalhesperfilusuario.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
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
