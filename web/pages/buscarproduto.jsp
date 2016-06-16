<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Buscar Produto</h1>
            </div>
            
        </div>
        <div class="row">
            <div class="col-lg-6">
                <form action="${pageContext.request.contextPath }/buscarprodutopornome.html" method="post">
                    <div class="col-xs-8">
                        <input type="text" class="form-control" placeholder="Nome do produto" name="busca" required>
                    </div>
                    <div class="col-xs-4">
                        <input type="submit" value="BUSCAR" class="btn btn-primary">
                    </div>
                </form>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-lg-6">
                <form action="${pageContext.request.contextPath }/buscarprodutoporcategoria.html" method="post">
                    <div class="col-xs-8">
                        <select class="form-control" name="categoria" required>
                            <option value="">--Selecione--</option>
                            <option value="1">Prato</option>
                            <option value="2">Bebida</option>
                            <option value="3">Sobremesa</option>
                        </select>
                    </div>
                    <div class="col-xs-4">
                        <input type="submit" value="BUSCAR" class="btn btn-primary">
                    </div>
                </form>
            </div>
        </div>
        <hr>
        
        <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Resultado da busca por "${busca}"
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tbbuscaproduto">
                                    <thead>
                                        <tr>
                                            <th>Nome do Produto</th>
                                            <th>Preço</th>
                                            <th>Avaliação Geral</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${lista}" var="p">
                                            <tr class="odd gradeX">
                                                <td>${p.nomeProduto}</td>
                                                <td>${p.preco}</td>
                                                <td></td>
                                                <td class="center">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.id != p.entidade.idresponsavel}">
                                                            <a href="${pageContext.request.contextPath }/detalhesproduto.html?id=${p.produtoid}" class="btn bg-info">
                                                                <span class="glyphicon glyphicon-eye-open"></span>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath }/editarproduto.html?id=${p.produtoid}" class="btn btn-info">
                                                                <span class="glyphicon glyphicon-edit"></span>
                                                            </a>
                                                            <a href="${pageContext.request.contextPath }/detalhesproduto.html?id=${p.produtoid}" class="btn btn-primary">
                                                                <span class="glyphicon glyphicon-eye-open"></span>
                                                            </a>
                                                            <a href="${pageContext.request.contextPath }/excluirproduto.html?id=${p.produtoid}" class="btn btn-danger" onclick="return confirm('Deseja excluir o Produto?')">
                                                                <span class="glyphicon glyphicon-remove"></span>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
            </div>
        
    </div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
    $(document).ready(function() {
        $('#tbbuscaproduto').DataTable({
                responsive: true
        });
    });
</script>