<jsp:include page="header.jsp"></jsp:include>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-8 jumbotron">
                <form action="${pageContext.request.contextPath }/editarperfil.html" method="post">
                    <input type="hidden" value="${pessoa.pessoaid}" name="idpessoa">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="thumbnail">
                                <img src="${pageContext.request.contextPath }/imagens/placeholder.jpg"/>
                            </div>
                        </div>
                    </div>
                    <hr>
                    ${msg}
                    <hr>
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-10">
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Nome</span>
                                <input type="text" class="form-control" maxlength="100" value="${pessoa.nome}" aria-describedby="basic-addon1" name="nome" id="nome" required>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-10">
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Sobrenome</span>
                                <input type="text" class="form-control" maxlength="100" value="${pessoa.sobrenome}" aria-describedby="basic-addon1" name="sobrenome" id="sobrenome" required>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-10">
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Email</span>
                                <input type="text" class="form-control" maxlength="100" value="${pessoa.login}" aria-describedby="basic-addon1" name="email" id="email" required>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-10">
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">CPF</span>
                                <input type="text" class="form-control" maxlength="100" value="${pessoa.cpf}" aria-describedby="basic-addon1" name="cpf" id="cpf" required>
                            </div>
                        </div>
                    </div>
                    <div class="row text-right" style="margin-bottom: 10px;">
                        <div class="col-xs-10">
                            <a href="" data-toggle="modal" class="btn btn-warning" data-target="#myModal">Trocar Senha</a>
                            <input type="submit" class="btn btn-primary" value="Salvar" >
                        </div>
                    </div>      
                </form>
                <!--popup senha-->
                <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel" id="myModal">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="gridSystemModalLabel">Trocar senha</h4>
                            </div>
                            <form action="${pageContext.request.contextPath }/mudarsenha.html" method="post">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-lg-12" style="margin-bottom: 10px">
                                            <input type="text" name="senha1" class="form-control" placeholder="Digite a senha" required> 
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <input type="text" name="senha2" class="form-control" placeholder="Repita a senha" required> 
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                                    <button type="submit" class="btn btn-primary">Salvar</button>
                                </div>
                            </form>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
                <!--/.popup senha-->
            </div>
        </div>
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>