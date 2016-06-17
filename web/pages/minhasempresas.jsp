<jsp:include page="header.jsp"></jsp:include>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Minhas Empresas</h1>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-10">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Resultados para <strong>Minhas Empresas</strong>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="tbminhasempresas">
                                <thead>
                                    <tr>
                                        <th>Nome da Empresa</th>
                                        <th>Endereço</th>
                                        <th>Avaliação Geral</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${lista}" var="e">
                                        <tr class="odd gradeX">
                                            <td>${e.nomeEmpresa}</td>
                                            <td>${e.endereco.rua}, ${e.endereco.numero} ${e.endereco.bairro} ${e.endereco.cidade} - ${e.endereco.estado} ${e.endereco.pais}</td>
                                            <td>${e.avaliacaoNota}</td>
                                            <td class="center">
                                                <a href="${pageContext.request.contextPath }/editarEmpresa.html?id=${e.empresaId}" class="btn btn-info">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                </a>
                                                <a href="${pageContext.request.contextPath }/detalhesempresa.html?id=${e.empresaId}" class="btn btn-primary">
                                                    <span class="glyphicon glyphicon-eye-open"></span>
                                                </a>
                                                <a href="${pageContext.request.contextPath }/excluirEmpresa.html?id=${e.empresaId}" class="btn btn-danger" onclick="return confirm('Deseja excluir a Empresa?')">
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
            </div>
        </div>
    </div>
    
<jsp:include page="footer.jsp"></jsp:include>
    <script>
        $(document).ready(function() {
            $('#tbminhasempresas').DataTable({
                    responsive: true
            });
        });
    </script>