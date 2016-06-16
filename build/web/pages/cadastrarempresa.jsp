<jsp:include page="header.jsp"></jsp:include>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Cadastrar Empresa</h1>
            </div>
            
            <div class="col-lg-8">
                ${msg}
                <form action="${pageContext.request.contextPath }/cadastrarempresa.html" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Nome:</h3>
                            <div class="input-group">
                                <input type="text" class="form-control" maxlength="100" placeholder="Nome da Empresa" aria-describedby="basic-addon1" name="nomeempresa" id="nomeempresa" required>
                                <span class="input-group-addon" id="basic-addon1">Ex.: Madero - Batel</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>CNPJ:</h3>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="CNPJ da Empresa" maxlength="25" name="cnpj" id="cnpj">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Descrição:</h3>
                            <textarea type="" class="form-control" rows="3" maxlength="1000" placeholder="Ex.: Restaurante chines com comida por kilo, etc.." name="descricao" id="descricao"></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>CEP:</h3>
                            <input type="text" class="form-control" placeholder="CEP do endereço" maxlength="10" name="cep" id="cep" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Rua:</h3>
                            <input type="text" class="form-control" placeholder="Rua do endereço" maxlength="80" name="rua" id="rua">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Número:</h3>
                            <input type="text" class="form-control" placeholder="Numero do endereço" maxlength="10" name="numero" id="numero">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Complemento:</h3>
                            <input type="text" class="form-control" placeholder="Ex.: Loja 123" maxlength="100" name="complemento" id="complemento">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Bairro:</h3>
                            <input type="text" class="form-control" placeholder="Bairro do endereço" maxlength="80" name="bairro" id="bairro">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Cidade:</h3>
                            <input type="text" class="form-control" placeholder="Cidade do endereço" maxlength="45" name="cidade" id="cidade">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Estado:</h3>
                            <input type="text" class="form-control" placeholder="Estado do endereço" maxlength="45" name="estado" id="estado">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>País:</h3>
                            <input type="text" class="form-control" placeholder="País do endereço" maxlength="45" name="pais" id="pais">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Telefone:</h3>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" placeholder="Tipo do telefone  Ex: Celular" maxlength="45" name="tipotelefone" id="tipotelefone" required>
                            </div>
                            <div class="col-xs-6">
                                <input type="text" class="form-control" placeholder="Numero do telefone" maxlength="30" name="telefone" id="telefone" required>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-10"><h3>Imagem de Perfil da Empresa:</h3></div>
                        <div class="col-xs-6 col-md-3">
                            <a class="thumbnail" onclick="clickLink()">
                                <img src="${pageContext.request.contextPath }/imagens/placeholder.jpg" alt="image" id="imagem">
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


<script type="text/javascript">
    $(function(){
            $("#cnpj").mask("99.999.999/9999-99");
            $("#cep").mask("99.999-999");
            $("#telefone").mask("(99)9999-9999");
    });
</script>