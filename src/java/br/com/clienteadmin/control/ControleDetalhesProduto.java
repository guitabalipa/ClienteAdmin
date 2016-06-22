/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import br.com.clienteadmin.model.Avaliacao;
import br.com.clienteadmin.model.Empresa;
import br.com.clienteadmin.model.Imagem;
import br.com.clienteadmin.model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "ControleDetalhesProduto", urlPatterns = {"/ControleDetalhesProduto", "/detalhesproduto.html", "/atualizar_avaliacoes_produto.html", 
            "/opcaoeditarproduto.html", "/editarproduto.html", "/excluirproduto.html"})
@MultipartConfig
public class ControleDetalhesProduto extends HttpServlet {

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
			
        if(url.equalsIgnoreCase("/detalhesproduto.html")) {
            
            Integer idproduto = new Integer(request.getParameter("id"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/pegarProdutoPorId/"+idproduto)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);

                Produto produto = gson.fromJson(resp, Produto.class);
                                
                request.setAttribute("produto", produto);
                request.getRequestDispatcher("pages/detalhesproduto.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/atualizar_avaliacoes_produto.html")) {
            
            Integer idproduto = new Integer(request.getParameter("idproduto"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/avaliacao/atualizarAvaliacoesProduto/"+idproduto)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                
                Type listType = new TypeToken<ArrayList<Avaliacao>>(){}.getType();
                List<Avaliacao> avaliacoes = gson.fromJson(resp, listType);
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(avaliacoes));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if(url.equalsIgnoreCase("/opcaoeditarproduto.html")) {
            
            Integer idproduto = new Integer(request.getParameter("id"));
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                Integer id   = new Integer(String.valueOf(session.getAttribute("id")));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/pegarProdutoPorId/"+idproduto)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                Produto produto = gson.fromJson(resp, Produto.class);
                
                String resp2 = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/buscarMinhasEmpresas/"+id)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);

                Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
                List<Empresa> empresas = gson.fromJson(resp2, listType);
                                
                request.setAttribute("empresas", empresas);
                request.setAttribute("produto", produto);
                request.getRequestDispatcher("pages/editarproduto.jsp").forward(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/editarproduto.html")) {
            
            String idproduto = request.getParameter("idproduto");
            String idempresa = request.getParameter("empresaid");
            String nomeproduto = request.getParameter("nomeproduto");
            String descricao = request.getParameter("descricao");
            String preco = request.getParameter("preco");
            String categoria = request.getParameter("categoria");
            String idimagem = request.getParameter("idimagem");
            Part arquivo = request.getPart("image");
            
            Produto p = new Produto();
            p.setProdutoid(new Integer(idproduto));
            p.setNomeProduto(nomeproduto);
            p.setDescricao(descricao);
            p.setPreco(new Double(preco.replace(",", ".")));
            p.setCategoria(new Integer(categoria));
            p.setEmpresaid(new Integer(idempresa));
            
            try {
                String nomeArquivo = "imgPerfil" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";

                InputStream inputStream = arquivo.getInputStream();

                byte[] conteudo = new byte[(int) arquivo.getSize()];

                if(conteudo.length > 0) {
                    inputStream.read(conteudo);
                    String img = Base64.encode(conteudo);

                    Imagem image = new Imagem();
                    image.setImagemid(new Integer(idimagem));
                    image.setNomeImagem(nomeArquivo);
                    image.setImg(img);
                    image.setTipoImagem(1);

                    p.setImagemPerfil(image);
                } else {
                    p.setImagemPerfil(null);
                }

                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/editarProdutoWeb")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(p), String.class);

                request.setAttribute("produto", gson.fromJson(resp, Produto.class));
                request.setAttribute("msg", "<div class='alert alert-info'>Produto editado com sucesso!</div>");
                request.getRequestDispatcher("pages/detalhesproduto.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/excluirproduto.html")) {
            
            Integer idproduto = new Integer(request.getParameter("id"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                client
                    .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/excluirProdutoWeb/"+idproduto)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Basic " + encodedCredentials)
                    .get(String.class);

                request.setAttribute("msg", "<div class='alert alert-info'>Produto excluido com sucesso!</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível</div>");
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
