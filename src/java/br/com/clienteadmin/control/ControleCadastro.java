/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import br.com.clienteadmin.model.Empresa;
import br.com.clienteadmin.model.Endereco;
import br.com.clienteadmin.model.Imagem;
import br.com.clienteadmin.model.Produto;
import br.com.clienteadmin.model.Telefone;
import br.com.clienteadmin.model.Usuario;
import com.google.gson.Gson;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.MultivaluedHashMap;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "ControleCadastro", urlPatterns = {"/ControleCadastro", "/cadastrarempresa.html", "/cadastrarproduto.html"})
@MultipartConfig
public class ControleCadastro extends HttpServlet {

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
			
        if(url.equalsIgnoreCase("/cadastrarempresa.html")) {
            
            String nomeempresa = request.getParameter("nomeempresa");
            String cnpj = request.getParameter("cnpj");
            String descricao = request.getParameter("descricao");
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");
            String tipotelefone = request.getParameter("tipotelefone");
            String telefone = request.getParameter("telefone");
            Part arquivo = request.getPart("image");
            
            try {
                Empresa e = new Empresa();
                e.setNomeEmpresa(nomeempresa);
                e.setCnpj(cnpj.replaceAll("[^0123456789]", ""));
                e.setDescricao(descricao);

                if(!cep.isEmpty()) {
                    Endereco end = new Endereco();
                    end.setCep(cep.replaceAll("[^0123456789]", ""));
                    end.setRua(rua);
                    end.setNumero(numero);
                    end.setComplemento(complemento);
                    end.setBairro(bairro);
                    end.setCidade(cidade);
                    end.setEstado(estado);
                    end.setPais(pais);
                    e.setEndereco(end);
                }

                if(!tipotelefone.isEmpty()) {
                    List<Telefone> tels = new ArrayList<>();
                    Telefone tel = new Telefone();
                    tel.setTipoTelefone(tipotelefone);
                    tel.setNumero(telefone.replaceAll("[^0123456789]", ""));
                    tels.add(tel);
                    e.setTelefones(tels);
                }

                String nomeArquivo = "imgPerfil" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";

                InputStream inputStream = arquivo.getInputStream();

                byte[] conteudo = new byte[(int) arquivo.getSize()];

                inputStream.read(conteudo);
                String img = Base64.encode(conteudo);

                Imagem image = new Imagem();
                image.setNomeImagem(nomeArquivo);
                image.setImg(img);
                image.setTipoImagem(1);

                e.setImagemPerfil(image);

                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/cadastrarEmpresaWeb")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(e), String.class);


                request.setAttribute("msg", "<div class='alert alert-info'>" + gson.fromJson(resp, String.class) + "</div>");
                request.getRequestDispatcher("pages/cadastrarempresa.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "<div class='alert alert-info'>Empresa não cadastrada.</div>");
                request.getRequestDispatcher("pages/cadastrarempresa.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/cadastrarproduto.html")) {
            
            String nomeproduto = request.getParameter("nomeproduto");
            String descricao = request.getParameter("descricao");
            String preco = request.getParameter("preco");
            String categoria = request.getParameter("categoria");
            Part arquivo = request.getPart("image");
            
            Produto p = new Produto();
            p.setNomeProduto(nomeproduto);
            p.setDescricao(descricao);
            p.setPreco(new Double(preco.replace(",", ".")));
            p.setCategoria(new Integer(categoria));
            
            try {
                String nomeArquivo = "imgPerfil" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";

                InputStream inputStream = arquivo.getInputStream();

                byte[] conteudo = new byte[(int) arquivo.getSize()];

                inputStream.read(conteudo);
                String img = Base64.encode(conteudo);

                Imagem image = new Imagem();
                image.setNomeImagem(nomeArquivo);
                image.setImg(img);
                image.setTipoImagem(1);

                p.setImagemPerfil(image);

                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Produto/cadastrarProdutoWeb")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(p), String.class);


                request.setAttribute("msg", "<div class='alert alert-info'>" + gson.fromJson(resp, String.class) + "</div>");
                request.getRequestDispatcher("pages/cadastrarproduto.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "<div class='alert alert-info'>Produto não cadastrado.</div>");
                request.getRequestDispatcher("pages/cadastrarproduto.jsp").forward(request, response);
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
