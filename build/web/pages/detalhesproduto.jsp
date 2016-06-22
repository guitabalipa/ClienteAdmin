<jsp:include page="header.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
    <div id="page-wrapper">
        <input type="hidden" value="${produto.produtoid}" id="idproduto">
        <div class="row">
            <div class="col-lg-12">
                <div class="col-sm-4 col-md-3">
                    <div class="thumbnail">
                        <!--<img src="${pageContext.request.contextPath }/imagens/placeholder.jpg" />-->
                        <img src="http://localhost:8080/ServidorAplicativo${produto.imagemPerfil.caminho}${produto.imagemPerfil.nomeImagem}" />
                    </div>
                </div>
                <div class="col-lg-8 col-md-4">
                    <div class="jumbotron" style="height: 210px">
                        <h2 style="padding-left: 50px">${produto.nomeProduto}</h2>
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
                                            <a href="${pageContext.request.contextPath }/opcaoeditarproduto.html?id=${produto.produtoid}"><i class="fa fa-edit fa-fw"></i> Editar</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath }/excluirproduto.html?id=${produto.produtoid}" onclick="return confirm('Tem certeza que deseja excluir esse produto?');"><i class="fa fa-times fa-fw"></i> Excluir</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="well well-lg">
                                <p>${produto.descricao}</p>
                            </div>
                            <hr>
                            <div class="well well-sm">
                                <p>Valor: ${produto.preco}</p>
                            </div>
                            <hr>
                            <div class="well well-lg">
                                <p>Empresa: ${produto.empresa.nomeEmpresa}</p>
                            </div>
                            <hr>
                            <div class="well well-lg">
                                <c:choose>
                                    <c:when test="${produto.categoria == 1}"><p>Categoria: Prato</p></c:when>
                                    <c:when test="${produto.categoria == 2}"><p>Categoria: Bebida</p></c:when>
                                    <c:otherwise><p>Categoria: Sobremesa</p></c:otherwise>
                                </c:choose>
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
                                <button type="button" class="btn btn-default btn-xs" onclick="atualizar_avaliacoes_produto()">
                                    <i class="fa fa-refresh fa-fw"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        
                        <!--panel-body-->
                        <div class="panel-body">
                            <ul class="chat" id="caixa-avaliacao">
                                <c:choose>
                                    <c:when test="${fn:length(produto.avaliacoes) > 0 }">
                                        <c:forEach items="${produto.avaliacoes}" var="av">
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
                </div>
                <!-- /.col-lg-4 -->
            </div>
        
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>
<script>
    $('.dropdown-toggle').dropdown();
</script>