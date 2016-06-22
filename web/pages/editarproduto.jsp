<jsp:include page="header.jsp"></jsp:include>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Editar Produto</h1>
            </div>
            
            <div class="col-lg-8">
                ${msg}
                <form action="${pageContext.request.contextPath }/editarproduto.html" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="idproduto" value="${produto.produtoid}">
                    <input type="hidden" name="idimagem" value="${produto.imagemPerfil.imagemid}">
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Empresa:</h3>
                            <select class="form-control" name="empresaid" required>
                                <option value="">--Selecione--</option>
                                <c:forEach items="${empresas}" var="e">
                                    <option value="${e.empresaId}"
                                            <c:if test="${produto.empresa.empresaId == e.empresaId}">selected</c:if>
                                            >${e.nomeEmpresa}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Nome:</h3>
                            <div class="input-group">
                                <input type="text" class="form-control" maxlength="100" value="${produto.nomeProduto}" aria-describedby="basic-addon1" name="nomeproduto" id="nomeproduto" required>
                                <span class="input-group-addon" id="basic-addon1">Ex.: Almôndegas</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Descrição:</h3>
                            <textarea type="" class="form-control" rows="3" maxlength="1000" name="descricao" id="descricao">${produto.descricao}</textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Preço:</h3>
                            <input type="text" class="form-control" value="${produto.preco}" maxlength="10" name="preco" id="preco">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Categoria:</h3>
                            <select class="form-control" name="categoria" required>
                                <option value="">--Selecione--</option>
                                <option value="1" <c:if test="${produto.categoria == 1}">selected</c:if>>Prato</option>
                                <option value="2" <c:if test="${produto.categoria == 2}">selected</c:if>>Bebida</option>
                                <option value="3" <c:if test="${produto.categoria == 3}">selected</c:if>>Sobremesa</option>
                            </select>
                        </div>
                    </div>
                    
                    
                    <div class="row">
                        <div class="col-xs-10"><h3>Imagem de Perfil do Produto:</h3></div>
                        <div class="col-xs-6 col-md-3">
                            <a class="thumbnail" onclick="clickLink()">
                                <img src="http://localhost:8080/ServidorAplicativo${produto.imagemPerfil.caminho}${produto.imagemPerfil.nomeImagem}" alt="image" id="imagem">
                            </a>
                            <input type="file" class="hide" name="image" id="image" accept="image/*" onchange="readURL(this);">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <div style="float: right">
                                <button type="submit" class="btn btn-primary">Cadastrar</button>
                            </div>
                        </div>
                     </div>
                </form>
            </div>
            
        </div>
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>