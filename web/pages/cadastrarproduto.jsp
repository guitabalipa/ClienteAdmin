<jsp:include page="header.jsp"></jsp:include>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Cadastrar Produto</h1>
            </div>
            
            <div class="col-lg-8">
                ${msg}
                <form action="${pageContext.request.contextPath }/cadastrarproduto.html" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Nome:</h3>
                            <div class="input-group">
                                <input type="text" class="form-control" maxlength="100" placeholder="Nome do Prduto" aria-describedby="basic-addon1" name="nomeproduto" id="nomeproduto" required>
                                <span class="input-group-addon" id="basic-addon1">Ex.: Almôndegas</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Descrição:</h3>
                            <textarea type="" class="form-control" rows="3" maxlength="1000" placeholder="Ex.: Carne ao molho.." name="descricao" id="descricao"></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Preço:</h3>
                            <input type="text" class="form-control" placeholder="Preço do produto" maxlength="10" name="preco" id="preco">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <h3>Categoria:</h3>
                            <select class="form-control" name="categoria" required>
                                <option value="">--Selecione--</option>
                                <option value="1">Prato</option>
                                <option value="2">Bebida</option>
                                <option value="3">Sobremesa</option>
                            </select>
                        </div>
                    </div>
                    
                    
                    <div class="row">
                        <div class="col-xs-10"><h3>Imagem de Perfil do Produto:</h3></div>
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