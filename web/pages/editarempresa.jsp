<jsp:include page="header.jsp"></jsp:include>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Editar Empresa</h1>
            </div>
            
            <div class="col-lg-8">
                ${msg}
                <form action="${pageContext.request.contextPath }/editarempresa.html" method="post" enctype="multipart/form-data">
                    <input type="hidden" value="${empresa.empresaId}" name="idempresa">
                    <input type="hidden" value="${empresa.endereco.enderecoid}" name="idendereco">
                    <input type="hidden" value="${empresa.imagemPerfil.imagemid}" name="idimagem">
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Nome:</h3>
                            <div class="input-group">
                                <input type="text" class="form-control" maxlength="100" value="${empresa.nomeEmpresa}" aria-describedby="basic-addon1" name="nomeempresa" id="nomeempresa" required>
                                <span class="input-group-addon" id="basic-addon1">Ex.: Madero - Batel</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>CNPJ:</h3>
                            <div class="form-group">
                                <input type="text" class="form-control" value="${empresa.cnpj}" maxlength="25" name="cnpj" id="cnpj">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Descrição:</h3>
                            <textarea type="" class="form-control" rows="3" maxlength="1000" name="descricao" id="descricao">${empresa.descricao}</textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>CEP:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.cep}" maxlength="10" name="cep" id="cep" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Rua:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.rua}" maxlength="80" name="rua" id="rua">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Número:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.numero}" maxlength="10" name="numero" id="numero">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Complemento:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.complemento}" maxlength="100" name="complemento" id="complemento">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Bairro:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.bairro}" maxlength="80" name="bairro" id="bairro">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Cidade:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.cidade}" maxlength="45" name="cidade" id="cidade">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Estado:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.estado}" maxlength="45" name="estado" id="estado">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>País:</h3>
                            <input type="text" class="form-control" value="${empresa.endereco.pais}" maxlength="45" name="pais" id="pais">
                        </div>
                    </div>
                        
                    <div class="row">
                        <c:forEach items="${empresa.telefones}" var="t">
                            <input type="hidden" value="${t.telefoneid}" name="idtelefone">
                            <div class="col-xs-10">
                                <h3>Telefone:</h3>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" value="${t.tipoTelefone}" maxlength="45" name="tipotelefone" id="tipotelefone" required>
                                </div>
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" value="${t.numero}" maxlength="30" name="telefone" id="telefone" required>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-10"><h3>Imagem de Perfil da Empresa:</h3></div>
                        <div class="col-xs-6 col-md-3">
                            <a class="thumbnail" onclick="clickLink()">
                                <img src="http://localhost:8080/ServidorAplicativo${empresa.imagemPerfil.caminho}${empresa.imagemPerfil.nomeImagem}" alt="image" id="imagem">
                            </a>
                            <input type="file" class="hide" name="image" id="image" accept="image/*" onchange="readURL(this);">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <div style="float: right">
                                <button type="submit" class="btn btn-primary">Salvar</button>
                            </div>
                        </div>
                     </div>
                </form>
            </div>
            
        </div>
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>


<script type="text/javascript">
    $(function(){
            $("#cnpj").mask("99.999.999/9999-99");
            $("#cep").mask("99.999-999");
            $("#telefone").mask("(99)9999-9999");
    });
</script>