<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Buscar Empresa</h1>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-6">
                <form action="${pageContext.request.contextPath }/buscarempresa.html" method="post">
                    <div class="col-xs-3">
                        <select class="form-control" name="busca" required>
                            <option value="">--Selecione--</option>
                            <option value="nome">Nome</option>
                            <option value="endereco">Endere�o</option>
                        </select>
                    </div>
                    <div class="col-xs-7">
                        <input type="text" class="form-control" placeholder="Palavra para a busca" name="palavra" required>
                    </div>
                    <div class="col-xs-2">
                        <input type="submit" value="BUSCAR" class="btn btn-primary">
                    </div>
                </form>
            </div>
        </div>
        <hr>
        
        ${msg}
        
        
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Resultado da busca por "${busca}"
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="tbbuscaempresa">
                                    <thead>
                                        <tr>
                                            <th>Nome da Empresa</th>
                                            <th>Endere�o</th>
                                            <th>Avalia��o Geral</th>
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
                                                    <c:choose>
                                                        <c:when test="${sessionScope.id != e.entidade.idresponsavel}">
                                                            <a href="${pageContext.request.contextPath }/detalhesempresa.html?id=${e.empresaId}" class="btn bg-info">
                                                                <span class="glyphicon glyphicon-eye-open"></span>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath }/editarEmpresa.html?id=${e.empresaId}" class="btn btn-info">
                                                                <span class="glyphicon glyphicon-edit"></span>
                                                            </a>
                                                            <a href="${pageContext.request.contextPath }/detalhesempresa.html?id=${e.empresaId}" class="btn btn-primary">
                                                                <span class="glyphicon glyphicon-eye-open"></span>
                                                            </a>
                                                            <a href="${pageContext.request.contextPath }/excluirEmpresa.html?id=${e.empresaId}" class="btn btn-danger" onclick="return confirm('Deseja excluir a Empresa?')">
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
        
<jsp:include page="footer.jsp"></jsp:include>
    <script>
        $(document).ready(function() {
            $('#tbbuscaempresa').DataTable({
                    responsive: true
            });
        });
    </script>