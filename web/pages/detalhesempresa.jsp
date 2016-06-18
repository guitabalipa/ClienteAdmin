<jsp:include page="header.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="col-sm-4 col-md-3">
                    <div class="thumbnail">
                        <img src="${pageContext.request.contextPath }/imagens/imgPerfil-1466040564260.jpg" />
                    </div>
                </div>
                <div class="col-lg-8 col-md-4">
                    <div class="jumbotron" style="height: 210px">
                        <h2 style="padding-left: 50px">${empresa.nomeEmpresa}</h2>
                    </div>
                </div>                    
            </div>
        </div>
        <hr>    
        
        <div class="row">
                <div class="col-lg-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Informações
                            <div class="pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Ações
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li>
                                            <a href="#"><i class="fa fa-edit fa-fw"></i> Editar</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="#"><i class="fa fa-times fa-fw"></i> Excluir</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="well well-lg">
                                <p>${empresa.descricao}</p>
                            </div>
                            <hr>
                            <div class="well well-sm">
                                <p>${empresa.endereco}</p>
                            </div>
                            <hr>
                            <div class="well well-lg">
                                <p>CNPJ: ${empresa.cnpj}</p>
                                <c:forEach items="${empresa.telefones}" var="t">
                                    <p>Telefone: ${t.tipoTelefone} - ${t.numero}</p>
                                </c:forEach>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-8 -->
                <div class="col-lg-4">
                    <div class="chat-panel panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-star fa-fw"></i>
                            Avaliações
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs">
                                    <i class="fa fa-refresh fa-fw"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        
                        <!--Painel de Comentarios-->
                        <div class="panel-body">
                            <ul class="chat">
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <img src="http://placehold.it/50/55C1E7/fff" alt="User Avatar" class="img-circle" />
                                    </span>
                                    <div class="chat-body clearfix">
                                        <div class="header">
                                            <strong class="primary-font">Lula da Silva</strong>
                                            <small class="pull-right text-muted">
                                                <i class="fa fa-clock-o fa-fw"></i>17/06/2016
                                            </small>
                                        </div>
                                        <p>
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer">
                            <div class="input-group">
                            </div>
                        </div>
                        <!-- /.panel-footer -->
                    </div>
                    <!-- /.panel -->
                    <div class="chat-panel panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i>
                            Comentários
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs">
                                    <i class="fa fa-refresh fa-fw"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        
                        <!--Painel de Comentarios-->
                        <div class="panel-body">
                            <ul class="chat">
                                <li class="clearfix">
                                    <div class="chat-body clearfix">
                                        <div class="header">
                                            <strong class="primary-font">Lula da Silva</strong>
                                            <small class="pull-right text-muted">
                                                <i class="fa fa-clock-o fa-fw"></i> 12 mins ago
                                            </small>
                                        </div>
                                        <p>
                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula sodales.
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer">
                            <div class="input-group">
                                <input id="btn-input" type="text" class="form-control input-sm" placeholder="Escreva seu comentário aqui..." />
                                <span class="input-group-btn">
                                    <button class="btn btn-warning btn-sm" id="btn-chat">
                                        Enviar
                                    </button>
                                </span>
                            </div>
                        </div>
                        <!-- /.panel-footer -->
                    </div>
                    <!-- /.panel .chat-panel -->
                </div>
                <!-- /.col-lg-4 -->
            </div>
        
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>