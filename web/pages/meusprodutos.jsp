<jsp:include page="header.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Meus Produtos</h1>
            </div>
            
        </div>
        
        <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Resultados para <strong>Meus Produtos</strong>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tbmeusprodutos">
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
                                                    <a href="${pageContext.request.contextPath }/editarproduto.html?id=${p.produtoid}" class="btn btn-info">
                                                        <span class="glyphicon glyphicon-edit"></span>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath }/detalhesproduto.html?id=${p.produtoid}" class="btn btn-primary">
                                                        <span class="glyphicon glyphicon-eye-open"></span>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath }/excluirproduto.html?id=${p.produtoid}" class="btn btn-danger" onclick="return confirm('Deseja excluir o Produto?')">
                                                        <span class="glyphicon glyphicon-remove"></span>
                                                    </a>
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
        $('#tbmeusprodutos').DataTable({
                responsive: true
        });
    });
</script>