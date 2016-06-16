/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import br.com.clienteadmin.model.Empresa;
import br.com.clienteadmin.model.Filtro;
import br.com.clienteadmin.model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "ControleBusca", urlPatterns = {"/ControleBusca", "/buscarempresa.html", "/buscarprodutopornome.html", "/buscarprodutoporcategoria.html"})
public class ControleBusca extends HttpServlet {

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
			
        if(url.equalsIgnoreCase("/buscarempresa.html")) {
            
            String busca = request.getParameter("busca");
            String palavra = request.getParameter("palavra");
            
            if(busca.equals("nome")) {
                
                List<Empresa> empresas = new ArrayList<>();
                Filtro f = new Filtro();
                
                f.setNomeempresa(palavra);
                
                try {
                    
                    HttpSession session = request.getSession();
                    String email = String.valueOf(session.getAttribute("email"));
                    String senha = String.valueOf(session.getAttribute("senha"));

                    String credentials = email + ":" + senha;
                    String encodedCredentials = Base64.encode(credentials.getBytes());

                    Client client = ClientBuilder.newClient();
                    String resp = client
                                    .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/buscarEmpresasJson")
                                    .request(MediaType.APPLICATION_JSON_TYPE)
                                    .header("Authorization", "Basic " + encodedCredentials)
                                    .post(Entity.json(f), String.class);

                    Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
                    empresas = gson.fromJson(resp, listType);
                    
                    request.setAttribute("lista", empresas);
                    request.setAttribute("busca", palavra);
                    request.getRequestDispatcher("pages/buscarempresa.jsp").forward(request, response);
                    
//                    PrintWriter out = response.getWriter();
//                    out.println(resp);
                    
                } catch(Exception e) {
                    e.printStackTrace();
                    
                    request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                    request.getRequestDispatcher("pages/buscarempresa.jsp").forward(request, response);
                }
                
            } else {
                
            }
        } else if(url.equalsIgnoreCase("/buscarprodutopornome.html")) {
            
            String busca = request.getParameter("busca");
            
             try {
                    
                List<Produto> produtos = new ArrayList<>();
                 
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/buscarProdutosPorNome")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(busca), String.class);

                Type listType = new TypeToken<ArrayList<Produto>>(){}.getType();
                produtos = gson.fromJson(resp, listType);

                request.setAttribute("lista", produtos);
                request.setAttribute("busca", busca);
                request.getRequestDispatcher("pages/buscarproduto.jsp").forward(request, response);

//                    PrintWriter out = response.getWriter();
//                    out.println(resp);

            } catch(Exception e) {
                e.printStackTrace();

                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/buscarproduto.jsp").forward(request, response);
            }
            
            
        } else if(url.equalsIgnoreCase("/buscarprodutoporcategoria.html")) {
            
            String categoria = request.getParameter("categoria");
            
            String busca = "";
            if(categoria.equals("1")) {
                busca = "Prato";
            } else if(categoria.equals("2")) {
                busca = "Bebida";
            } else {
                busca = "Sobremesa";
            }
            
            try {
                    
                List<Produto> produtos = new ArrayList<>();
                 
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/buscarProdutosPorCategoria")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(categoria), String.class);

                Type listType = new TypeToken<ArrayList<Produto>>(){}.getType();
                produtos = gson.fromJson(resp, listType);

                request.setAttribute("lista", produtos);
                request.setAttribute("busca", busca);
                request.getRequestDispatcher("pages/buscarproduto.jsp").forward(request, response);

//                    PrintWriter out = response.getWriter();
//                    out.println(resp);

            } catch(Exception e) {
                e.printStackTrace();

                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/buscarproduto.jsp").forward(request, response);
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
