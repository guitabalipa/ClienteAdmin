<jsp:include page="header.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
    <div id="page-wrapper">
        <input type="hidden" value="${empresa.empresaId}" id="idempresa">
        <input type="hidden" value="${sessionScope.id}" id="idusuario">
        <div class="row">
            <div class="col-lg-12">
                <div class="col-sm-4 col-md-3">
                    <div class="thumbnail">
                        <img src="http://localhost:8080/ServidorAplicativo${empresa.imagemPerfil.caminho}${empresa.imagemPerfil.nomeImagem}" />
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
                                            <a href="${pageContext.request.contextPath }/opcaoeditar.html?id=${empresa.empresaId}"><i class="fa fa-edit fa-fw"></i> Editar</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath }/excluirempresa.html?id=${empresa.empresaId}" onclick="return confirm('Tem certeza que deseja excluir essa empresa?');"><i class="fa fa-times fa-fw"></i> Excluir</a>
                                        </li>
<!--                                        <li class="divider"></li>
                                        <li>
                                            <a href="" data-toggle="modal" data-target="#myModal"><i class="fa fa-calendar-plus-o fa-fw"></i> Adicionar Programação</a>
                                        </li>-->
                                    </ul>
                                </div>
                            </div>
                        </div>
                            
                        <!--popup programacao-->
                        <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel" id="myModal">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="gridSystemModalLabel">Programação</h4>
                                    </div>
                                    <form action="${pageContext.request.contextPath }/inserirprogramacao.html" method="post">
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <textarea class="form-control" rows="4" cols="auto" maxlength="500" placeholder="digite aqui a programação da sua empresa" name="programacao" id="programacao"></textarea>
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
                        <!--/.popup programacao-->
                                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="well well-lg">
                                <p>${empresa.descricao}</p>
                            </div>
                            <hr>
                            <div class="well well-sm">
                                <p>${empresa.endereco.rua}, ${empresa.endereco.numero}. ${empresa.endereco.bairro}. ${empresa.endereco.cidade}/${empresa.endereco.estado} - ${empresa.endereco.pais}</p>
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
                        
                <!--Painel de Avaliacoes-->
                <div class="col-lg-4">
                    <div class="chat-panel panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-star fa-fw"></i>
                            Avaliações
                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-default btn-xs" onclick="atualizar_avaliacoes()">
                                    <i class="fa fa-refresh fa-fw"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        
                        <!--panel-body-->
                        <div class="panel-body">
                            <ul class="chat" id="caixa-avaliacao">
                                <c:choose>
                                    <c:when test="${fn:length(empresa.avaliacoes) > 0 }">
                                        <c:forEach items="${empresa.avaliacoes}" var="av">
                                            <li class="left clearfix">
                                                <span class="pull-left">
                                                    <h1 style="margin-top: 0px">${av.nota}</h1>
                                                </span>
                                                <div class="chat-body clearfix">
                                                    <div class="header">
                                                        <strong class="primary-font">${av.pessoa.nome} ${av.pessoa.sobrenome}</strong>
                                                        <small class="pull-right text-muted">
                                                            <i class="fa fa-clock-o fa-fw"></i><fmt:formatDate pattern="dd/MM/yyyy" value="${av.data_modificacao}" />
                                                        </small>
                                                    </div>
                                                    <p style="word-wrap: break-word;">${av.descricao}</p>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="clearfix">
                                            <div class="chat-body clearfix">
                                                <p class="text-center"><i class="fa fa-ban fa-fw"></i> Nenhuma Avaliação</p>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
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
                                <button type="button" class="btn btn-default btn-xs" onclick="atualizar_comentarios()">
                                    <i class="fa fa-refresh fa-fw"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        
                        <!--Painel de Comentarios-->
                        <div class="panel-body">
                            <ul class="chat" id="caixa-comentario">
                                <c:choose>
                                    <c:when test="${fn:length(empresa.comentarios) > 0 }">
                                        <c:forEach items="${empresa.comentarios}" var="com">
                                            <li class="clearfix">
                                                <div class="chat-body clearfix">
                                                    <div class="header">
                                                        <strong class="primary-font">${com.pessoa.nome} ${com.pessoa.sobrenome}</strong>
                                                        <small class="pull-right text-muted">
                                                            <i class="fa fa-clock-o fa-fw"></i><fmt:formatDate pattern="dd/MM/yyyy" value="${com.data_modificacao}" />
                                                        </small>
                                                    </div>
                                                    <div>
                                                        <p style="word-wrap: break-word;">${com.descricao}</p>
                                                        <c:choose>
                                                            <c:when test="${com.pessoa.pessoaid == sessionScope.id}">
                                                                <a href="javascript:void(0);" onclick="excluir_comentario(${com.comentarioid});">
                                                                    <small class="pull-right text-muted">
                                                                        <i class="fa fa-times fa-fw"></i>Excluir
                                                                    </small>
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div id="responder-coment-${com.comentarioid}">
                                                                    <a href="javascript:void(0);" onclick="responder_comentario(${com.comentarioid});">
                                                                        <small class="pull-right text-muted">
                                                                            <i class="fa fa-reply fa-fw"></i>Responder
                                                                        </small>
                                                                    </a>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        
                                                        <c:if test="${com.cont_comentarios_dependentes > 0}">
                                                            <div id="responder-coment-${com.comentarioid}" style="float: left">
                                                                <a href="javascript:void(0);" onclick="ver_respostas(${com.comentarioid});">
                                                                    <small class="pull-right text-muted">
                                                                        Ver Respostas<span id="toggle-response-${com.comentarioid}"><i class="fa fa-caret-down fa-fw"></i></span>
                                                                    </small>
                                                                </a>
                                                                
                                                            </div>
                                                            
                                                            <div class="col-lg-12 hide" id="respostas-comentarios-${com.comentarioid}"></div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <div class="input-group hide" id="caixa-resposta-${com.comentarioid}">
                                                    <input type="text" class="form-control input-sm" placeholder="Escreva sua resposta aqui.." id="comentario-resposta-${com.comentarioid}" />
                                                    <span class="input-group-btn">
                                                        <button class="btn btn-warning btn-sm" onclick="resposta_comentario(${com.comentarioid});">Enviar</button>
                                                    </span>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="clearfix">
                                            <div class="chat-body clearfix">
                                                <p class="text-center"><i class="fa fa-ban fa-fw"></i> Nenhum Comentário</p>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>
                        <!-- /.panel-body -->
                        <div class="panel-footer">
                            <div class="input-group">
                                <input type="text" class="form-control input-sm" placeholder="Escreva seu comentário aqui..." id="comentario-dsc" />
                                <span class="input-group-btn">
                                    <button class="btn btn-warning btn-sm" id="btn-comentario">
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
<script>
    $('.dropdown-toggle').dropdown();
</script>