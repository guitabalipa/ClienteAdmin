/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.clienteadmin.control;

import br.com.clienteadmin.model.Avaliacao;
import br.com.clienteadmin.model.Comentario;
import br.com.clienteadmin.model.Empresa;
import br.com.clienteadmin.model.Endereco;
import br.com.clienteadmin.model.Imagem;
import br.com.clienteadmin.model.Telefone;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.xml.wss.impl.misc.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
@WebServlet(name = "ControleDetalhes", urlPatterns = {"/ControleDetalhes", "/detalhesempresa.html", "/addcomentario.html", "/respondercomentario.html", 
                    "/respostascomentario.html", "/excluir_comentario.html", "/atualizar_comentarios.html", "/atualizar_avaliacoes.html", 
                    "/opcaoeditar.html", "/editarempresa.html", "/excluirempresa.html"})
@MultipartConfig
public class ControleDetalhes extends HttpServlet {

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
			
        if(url.equalsIgnoreCase("/detalhesempresa.html")) {
            
            Integer id = new Integer(request.getParameter("id"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/pegarEmpresaPorId/"+id)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);

//                PrintWriter out = response.getWriter();
//                out.println(resp);
                Empresa empresa = gson.fromJson(resp, Empresa.class);
                                
                request.setAttribute("empresa", empresa);
                request.getRequestDispatcher("pages/detalhesempresa.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível no momento</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
        } else if(url.equalsIgnoreCase("/addcomentario.html")) {
            
            String descricao = request.getParameter("descricao");
            Integer idempresa = new Integer(request.getParameter("idempresa"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                String id    = String.valueOf(session.getAttribute("id"));
                
                Comentario com = new Comentario();
                com.setComentadoid(idempresa);
                com.setDescricao(descricao);
                com.setPessoaid(new Integer(id));
                com.setComentarioDependenteid(0);
                com.setTipoComentado("empresa");

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/setComentarioJson")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(com), String.class);
                
                String resp2 = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/getComentariosByIdEmpresaJson/"+idempresa)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                Type listType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                List<Comentario> comentarios = gson.fromJson(resp2, listType);
                Collections.sort(comentarios, (Comentario obj1, Comentario obj2) -> obj2.getData_modificacao().compareTo(obj1.getData_modificacao()));
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(comentarios));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if(url.equalsIgnoreCase("/respondercomentario.html")) {
            
            String descricao = request.getParameter("descricao");
            Integer idempresa = new Integer(request.getParameter("idempresa"));
            Integer idcomentario = new Integer(request.getParameter("idcomentario"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));
                String id    = String.valueOf(session.getAttribute("id"));
                
                Comentario com = new Comentario();
                com.setComentadoid(idempresa);
                com.setComentarioDependenteid(idcomentario);
                com.setDescricao(descricao);
                com.setPessoaid(new Integer(id));
                com.setTipoComentado("empresa");

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/setComentarioJson")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(com), String.class);
                
                String resp2 = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/getComentariosByIdEmpresaJson/"+idempresa)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                Type listType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                List<Comentario> comentarios = gson.fromJson(resp2, listType);
                Collections.sort(comentarios, (Comentario obj1, Comentario obj2) -> obj2.getData_modificacao().compareTo(obj1.getData_modificacao()));
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(comentarios));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if(url.equalsIgnoreCase("/respostascomentario.html")) {
            
            Integer idcomentario = new Integer(request.getParameter("idcomentario"));
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/pegarComentariosDependentes/"+idcomentario)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                
                Type listType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                List<Comentario> comentarios = gson.fromJson(resp, listType);
                Collections.sort(comentarios, (Comentario obj1, Comentario obj2) -> obj2.getData_modificacao().compareTo(obj1.getData_modificacao()));
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(comentarios));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        } else if(url.equalsIgnoreCase("/excluir_comentario.html")) {
            
            Integer idcomentario = new Integer(request.getParameter("idcomentario"));
            Integer idempresa = new Integer(request.getParameter("idempresa"));
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/excluirComentario/"+idempresa+"/"+idcomentario)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                
                Type listType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                List<Comentario> comentarios = gson.fromJson(resp, listType);
                Collections.sort(comentarios, (Comentario obj1, Comentario obj2) -> obj2.getData_modificacao().compareTo(obj1.getData_modificacao()));
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(comentarios));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if(url.equalsIgnoreCase("/atualizar_comentarios.html")) {
            
            Integer idempresa = new Integer(request.getParameter("idempresa"));
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/comentario/atualizarComentarios/"+idempresa)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);
                
                
                Type listType = new TypeToken<ArrayList<Comentario>>(){}.getType();
                List<Comentario> comentarios = gson.fromJson(resp, listType);
                Collections.sort(comentarios, (Comentario obj1, Comentario obj2) -> obj2.getData_modificacao().compareTo(obj1.getData_modificacao()));
                
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(comentarios));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        } else if(url.equalsIgnoreCase("/atualizar_avaliacoes.html")) {
            
            Integer idempresa = new Integer(request.getParameter("idempresa"));
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/avaliacao/atualizarAvaliacoes/"+idempresa)
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
            
        } else if(url.equalsIgnoreCase("/opcaoeditar.html")) {
            Integer id = new Integer(request.getParameter("id"));
            
            try {
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/pegarEmpresaPorId/"+id)
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .get(String.class);

                                
                request.setAttribute("empresa", gson.fromJson(resp, Empresa.class));
                request.getRequestDispatcher("pages/editarempresa.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                
                request.setAttribute("msg", "Servidor indisponível");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
            
        } else if(url.equalsIgnoreCase("/editarempresa.html")) {
            
            String idempresa = request.getParameter("idempresa");
            String nomeempresa = request.getParameter("nomeempresa");
            String cnpj = request.getParameter("cnpj");
            String descricao = request.getParameter("descricao");
            String idendereco = request.getParameter("idendereco");
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String pais = request.getParameter("pais");
            String idtelefone = request.getParameter("idtelefone");
            String tipotelefone = request.getParameter("tipotelefone");
            String telefone = request.getParameter("telefone");
            String idimagem = request.getParameter("idimagem");
            Part arquivo = request.getPart("image");
            
            try {
                Empresa e = new Empresa();
                e.setEmpresaId(new Integer(idempresa));
                e.setNomeEmpresa(nomeempresa);
                e.setCnpj(cnpj.replaceAll("[^0123456789]", ""));
                e.setDescricao(descricao);

                if(!cep.isEmpty()) {
                    Endereco end = new Endereco();
                    end.setEnderecoid(new Integer(idendereco));
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
                    tel.setTelefoneid(new Integer(idtelefone));
                    tel.setTipoTelefone(tipotelefone);
                    tel.setNumero(telefone.replaceAll("[^0123456789]", ""));
                    tels.add(tel);
                    e.setTelefones(tels);
                }

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

                    e.setImagemPerfil(image);
                } else {
                    e.setImagemPerfil(null);
                }

                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                String resp = client
                                .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/atualizarEmpresaWeb")
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .header("Authorization", "Basic " + encodedCredentials)
                                .post(Entity.json(e), String.class);

                                
                request.setAttribute("empresa", gson.fromJson(resp, Empresa.class));
                request.setAttribute("msg", "<div class='alert alert-info'>Empresa editada com sucesso!</div>");
                request.getRequestDispatcher("pages/detalhesempresa.jsp").forward(request, response);
                
            } catch(Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "<div class='alert alert-info'>Servidor indisponível</div>");
                request.getRequestDispatcher("pages/index.jsp").forward(request, response);
            }
        } else if(url.equalsIgnoreCase("/excluirempresa.html")) {
            
            String idempresa = request.getParameter("id");
            
            try {
                
                HttpSession session = request.getSession();
                String email = String.valueOf(session.getAttribute("email"));
                String senha = String.valueOf(session.getAttribute("senha"));

                String credentials = email + ":" + senha;
                String encodedCredentials = Base64.encode(credentials.getBytes());

                Client client = ClientBuilder.newClient();
                client
                    .target("http://localhost:8080/ServidorAplicativo/webresources/Empresa/excluirEmpresaWeb")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Basic " + encodedCredentials)
                    .post(Entity.json(idempresa), String.class);

                                
                request.setAttribute("msg", "<div class='alert alert-info'>Empresa excluida com sucesso!</div>");
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
