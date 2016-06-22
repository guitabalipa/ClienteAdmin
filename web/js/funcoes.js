$( document ).ready(function() {
    
    // Busca endereco pelo cep usando postmon e seta os valores nos campos
    $("#cep").on("change", function () {
        var cep = $("#cep").val();        
        var numsStr = cep.replace(/[^0-9]/g,'');
        cep = parseInt(numsStr);

        $.ajax({
            url: "http://api.postmon.com.br/v1/cep/"+cep
        }).done(function(data) {
            $("#rua").val(data.logradouro);
            $("#bairro").val(data.bairro);
            $("#cidade").val(data.cidade);
            $("#estado").val(data.estado);
            $("#pais").val("Brasil");
        });
    });
    
    $("#btn-comentario").on("click", function () {
        var descricao = $("#comentario-dsc").val();
        var idempresa = $("#idempresa").val();
        var idusuario = $("#idusuario").val();
        
        $.ajax({
            method: "POST",
            url: "addcomentario.html",
            data: { descricao : descricao, idempresa : idempresa }
        }).done(function(data) {
            var objs = JSON.parse(data);
            var html = "";
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var data_modificacao = objs[i].data_modificacao;
                var coment_idusuario = objs[i].pessoaid;
                var idcomentario = objs[i].comentarioid;
                var coment_dependente = objs[i].cont_comentarios_dependentes;
            
                var resp = "";
                var link = "";
                if(idusuario == coment_idusuario) {
                    link = '<a href="javascript:void(0);" onclick="excluir_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-times fa-fw"></i>Excluir'+
                                '</small>'+
                            '</a>';
                } else {
                    link = '<a href="javascript:void(0);" onclick="responder_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-reply fa-fw"></i>Responder'+
                                '</small>'+
                            '</a>';
                }
                
                if(coment_dependente > 0) {
                    resp += '<div id="responder-coment-'+idcomentario+'" style="float: left">'
                                + '<a href="javascript:void(0);" onclick="ver_respostas('+idcomentario+');">'
                                        + '<small class="pull-right text-muted">'
                                            + 'Ver Respostas<span id="toggle-response-'+idcomentario+'"><i class="fa fa-caret-down fa-fw"></i></span>'
                                        + '</small>'
                                + '</a>'
                            + '</div>'

                            + '<div class="col-lg-12 hide" id="respostas-comentarios-'+idcomentario+'"></div>'
                }
            
                html += '<li class="clearfix">'+
                    '<div class="chat-body clearfix">'+
                        '<div class="header">'+
                            '<strong class="primary-font">'+nome +' '+sobrenome+'</strong>'+
                            '<small class="pull-right text-muted">'+
                                '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)+
                            '</small>'+
                        '</div>'+
                        '<div>'+
                            '<p style="word-wrap: break-word;">'+descricao+'</p>'+
                            link+
                            resp+
                        '</div>'+
                    '</div>'+
                    '<div class="input-group hide" id="caixa-resposta-'+idcomentario+'">'+
                        '<input type="text" class="form-control input-sm" placeholder="Escreva sua resposta aqui.." id="comentario-resposta-'+idcomentario+'" />'+
                        '<span class="input-group-btn">'+
                            '<button class="btn btn-warning btn-sm" onclick="resposta_comentario('+idcomentario+');">Enviar</button>'+
                        '</span>'+
                    '</div>'+
                '</li>';
            }
            $("#comentario-dsc").val("");
            $("#caixa-comentario").html(html);
        });
    });
    
    $("#responder-coment").on("click", function () {
        $("#caixa-resposta").removeClass("hide");
        $("#responder-coment").hide();
    });
    
});

function clickLink() {
    $("#image").click();
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imagem')
                    .attr('src', e.target.result)
                    .width(170)
                    .height(180);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function dataFormatada(date){
    var data = new Date(date);
    var dia = data.getDate();
    if (dia.toString().length === 1)
        dia = "0" + dia;
    var mes = data.getMonth() + 1;
    if (mes.toString().length === 1)
        mes = "0" + mes;
    var ano = data.getFullYear();  
    return dia+"/"+mes+"/"+ano;
}

function responder_comentario(id) {
    $("#caixa-resposta-"+id).removeClass("hide");
}

function resposta_comentario(id) {
    var descricao = $("#comentario-resposta-"+id).val();
    var idempresa = $("#idempresa").val();
    var idusuario = $("#idusuario").val();
    
    
    $.ajax({
        method: "POST",
        url: "respondercomentario.html",
        data: { descricao : descricao, idcomentario : id, idempresa : idempresa }
    }).done(function(data) {
            var objs = JSON.parse(data);
            var html = "";
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var data_modificacao = objs[i].data_modificacao;
                var coment_idusuario = objs[i].pessoaid;
                var coment_dependente = objs[i].cont_comentarios_dependentes;
                var idcomentario = objs[i].comentarioid;
            
                var resp = "";
                var link = "";
                if(idusuario == coment_idusuario) {
                    link = '<a href="javascript:void(0);" onclick="excluir_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-times fa-fw"></i>Excluir'+
                                '</small>'+
                            '</a>';
                } else {
                    link = '<a href="javascript:void(0);" onclick="responder_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-reply fa-fw"></i>Responder'+
                                '</small>'+
                            '</a>';
                }
                
                if(coment_dependente > 0) {
                    resp += '<div id="responder-coment-'+idcomentario+'" style="float: left">'
                                + '<a href="javascript:void(0);" onclick="ver_respostas('+idcomentario+');">'
                                        + '<small class="pull-right text-muted">'
                                            + 'Ver Respostas<span id="toggle-response-'+idcomentario+'"><i class="fa fa-caret-down fa-fw"></i></span>'
                                        + '</small>'
                                + '</a>'
                            + '</div>'

                            + '<div class="col-lg-12 hide" id="respostas-comentarios-'+idcomentario+'"></div>'
                }
            
                html += '<li class="clearfix">'+
                    '<div class="chat-body clearfix">'+
                        '<div class="header">'+
                            '<strong class="primary-font">'+nome +' '+sobrenome+'</strong>'+
                            '<small class="pull-right text-muted">'+
                                '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)+
                            '</small>'+
                        '</div>'+
                        '<div>'+
                            '<p style="word-wrap: break-word;">'+descricao+'</p>'+
                            link+
                            resp+
                        '</div>'+
                    '</div>'+
                    '<div class="input-group hide" id="caixa-resposta-'+idcomentario+'">'+
                        '<input type="text" class="form-control input-sm" placeholder="Escreva sua resposta aqui.." id="comentario-resposta-'+idcomentario+'" />'+
                        '<span class="input-group-btn">'+
                            '<button class="btn btn-warning btn-sm" onclick="resposta_comentario('+idcomentario+');">Enviar</button>'+
                        '</span>'+
                    '</div>'+
                '</li>';
            }
            $("#comentario-dsc").val("");
            $("#comentario-resposta-"+id).html("");
            $("#caixa-comentario").html(html);
    });
}

function ver_respostas(id) {
    var toggle = $("#toggle-response-"+id).html();
    var idusuario = $("#idusuario").val();
    
    if(toggle == '<i class="fa fa-caret-up fa-fw"></i>') {
        $("#respostas-comentarios-"+id).addClass("hide");
        $("#toggle-response-"+id).html('<i class="fa fa-caret-down fa-fw"></i>');
    } else {
        $.ajax({
            method: "POST",
            url: "respostascomentario.html",
            data: { idcomentario : id }
        }).done(function(data) {
            var objs = JSON.parse(data);
            var html = "";
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var data_modificacao = objs[i].data_modificacao;
                var coment_idusuario = objs[i].pessoa.pessoaid;
                var idcoment = objs[i].comentarioid;
                
                var link = "";
                if(idusuario == coment_idusuario) {
                    link = '<a href="javascript:void(0);" onclick="excluir_comentario('+idcoment+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-times fa-fw"></i>Excluir'+
                                '</small>'+
                            '</a>';
                }
                
                html += '<hr>'
                        + '<ul class="chat">'
                            + '<li class="clearfix">'
                                + '<div class="chat-body clearfix">'
                                    + '<div class="header">'
                                        + '<strong class="primary-font">'+nome+' '+sobrenome+'</strong>'
                                        + '<small class="pull-right text-muted">'
                                            + '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)
                                        + '</small>'
                                    + '</div>'
                                    + '<div>'
                                        + '<p style="word-wrap: break-word;">'+descricao+'</p>'
                                        + link
                                    + '</div>'
                                + '</div>'
                            + '</li>'
                        + '</ul>';
                
            }
            $("#respostas-comentarios-"+id).html(html);
            $("#respostas-comentarios-"+id).removeClass("hide");
            $("#toggle-response-"+id).html('<i class="fa fa-caret-up fa-fw"></i>');
        });
    }
}

function excluir_comentario(id) {
    var idempresa = $("#idempresa").val();
    var idusuario = $("#idusuario").val();
    $.ajax({
        method: "POST",
        url: "excluir_comentario.html",
        data: { idempresa : idempresa, idcomentario : id }
    }).done(function(data) {
            var objs = JSON.parse(data);
            var html = "";
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var data_modificacao = objs[i].data_modificacao;
                var coment_idusuario = objs[i].pessoaid;
                var idcomentario = objs[i].comentarioid;
                var coment_dependente = objs[i].cont_comentarios_dependentes;
            
                var resp = "";
                var link = "";
                if(idusuario == coment_idusuario) {
                    link = '<a href="javascript:void(0);" onclick="excluir_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-times fa-fw"></i>Excluir'+
                                '</small>'+
                            '</a>';
                } else {
                    link = '<a href="javascript:void(0);" onclick="responder_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-reply fa-fw"></i>Responder'+
                                '</small>'+
                            '</a>';
                }
                
                if(coment_dependente > 0) {
                    resp += '<div id="responder-coment-'+idcomentario+'" style="float: left">'
                                + '<a href="javascript:void(0);" onclick="ver_respostas('+idcomentario+');">'
                                        + '<small class="pull-right text-muted">'
                                            + 'Ver Respostas<span id="toggle-response-'+idcomentario+'"><i class="fa fa-caret-down fa-fw"></i></span>'
                                        + '</small>'
                                + '</a>'
                            + '</div>'

                            + '<div class="col-lg-12 hide" id="respostas-comentarios-'+idcomentario+'"></div>'
                }
            
                html += '<li class="clearfix">'+
                    '<div class="chat-body clearfix">'+
                        '<div class="header">'+
                            '<strong class="primary-font">'+nome +' '+sobrenome+'</strong>'+
                            '<small class="pull-right text-muted">'+
                                '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)+
                            '</small>'+
                        '</div>'+
                        '<div>'+
                            '<p style="word-wrap: break-word;">'+descricao+'</p>'+
                            link+
                            resp+
                        '</div>'+
                    '</div>'+
                    '<div class="input-group hide" id="caixa-resposta-'+idcomentario+'">'+
                        '<input type="text" class="form-control input-sm" placeholder="Escreva sua resposta aqui.." id="comentario-resposta-'+idcomentario+'" />'+
                        '<span class="input-group-btn">'+
                            '<button class="btn btn-warning btn-sm" onclick="resposta_comentario('+idcomentario+');">Enviar</button>'+
                        '</span>'+
                    '</div>'+
                '</li>';
            }
            $("#comentario-dsc").val("");
            $("#caixa-comentario").html(html);
    });
}

function atualizar_comentarios() {
    var idempresa = $("#idempresa").val();
    var idusuario = $("#idusuario").val();
    $.ajax({
        method: "POST",
        url: "atualizar_comentarios.html",
        data: { idempresa : idempresa }
    }).done(function(data) {
            var objs = JSON.parse(data);
            var html = "";
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var data_modificacao = objs[i].data_modificacao;
                var coment_idusuario = objs[i].pessoaid;
                var idcomentario = objs[i].comentarioid;
                var coment_dependente = objs[i].cont_comentarios_dependentes;
            
                var resp = "";
                var link = "";
                if(idusuario == coment_idusuario) {
                    link = '<a href="javascript:void(0);" onclick="excluir_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-times fa-fw"></i>Excluir'+
                                '</small>'+
                            '</a>';
                } else {
                    link = '<a href="javascript:void(0);" onclick="responder_comentario('+idcomentario+');">'+
                                '<small class="pull-right text-muted">'+
                                    '<i class="fa fa-reply fa-fw"></i>Responder'+
                                '</small>'+
                            '</a>';
                }
                
                if(coment_dependente > 0) {
                    resp += '<div id="responder-coment-'+idcomentario+'" style="float: left">'
                                + '<a href="javascript:void(0);" onclick="ver_respostas('+idcomentario+');">'
                                        + '<small class="pull-right text-muted">'
                                            + 'Ver Respostas<span id="toggle-response-'+idcomentario+'"><i class="fa fa-caret-down fa-fw"></i></span>'
                                        + '</small>'
                                + '</a>'
                            + '</div>'

                            + '<div class="col-lg-12 hide" id="respostas-comentarios-'+idcomentario+'"></div>'
                }
            
                html += '<li class="clearfix">'+
                    '<div class="chat-body clearfix">'+
                        '<div class="header">'+
                            '<strong class="primary-font">'+nome +' '+sobrenome+'</strong>'+
                            '<small class="pull-right text-muted">'+
                                '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)+
                            '</small>'+
                        '</div>'+
                        '<div>'+
                            '<p style="word-wrap: break-word;">'+descricao+'</p>'+
                            link+
                            resp+
                        '</div>'+
                    '</div>'+
                    '<div class="input-group hide" id="caixa-resposta-'+idcomentario+'">'+
                        '<input type="text" class="form-control input-sm" placeholder="Escreva sua resposta aqui.." id="comentario-resposta-'+idcomentario+'" />'+
                        '<span class="input-group-btn">'+
                            '<button class="btn btn-warning btn-sm" onclick="resposta_comentario('+idcomentario+');">Enviar</button>'+
                        '</span>'+
                    '</div>'+
                '</li>';
            }
            $("#comentario-dsc").val("");
            $("#caixa-comentario").html(html);
    });
}

function atualizar_avaliacoes() {
    var idempresa = $("#idempresa").val();
    $.ajax({
        method: "POST",
        url: "atualizar_avaliacoes.html",
        data: { idempresa : idempresa }
    }).done(function(data) {
        var objs = JSON.parse(data);
        var html = "";
        if(objs != null) {
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var nota = objs[i].nota;
                var data_modificacao = objs[i].data_modificacao;
                
                html += '<li class="left clearfix">'
                            + '<span class="pull-left">'
                                + '<h1 style="margin-top: 0px">'+nota+'</h1>'
                            + '</span>'
                            + '<div class="chat-body clearfix">'
                                + '<div class="header">'
                                    + '<strong class="primary-font">'+nome+' '+sobrenome+'</strong>'
                                    + '<small class="pull-right text-muted">'
                                        + '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)
                                    + '</small>'
                                + '</div>'
                                + '<p style="word-wrap: break-word;">'+descricao+'</p>'
                            + '</div>'
                        + '</li>';
                
            }
        } else {
            
            html += '<li class="clearfix">'
                        + '<div class="chat-body clearfix">'
                            + '<p class="text-center"><i class="fa fa-ban fa-fw"></i> Nenhuma Avaliação</p>'
                        + '</div>'
                    + '</li>';
        }
        $("#caixa-avaliacao").html(html);        
        
    });
}

function atualizar_avaliacoes_produto() {
    var idproduto = $("#idproduto").val();
    $.ajax({
        method: "POST",
        url: "atualizar_avaliacoes_produto.html",
        data: { idproduto : idproduto }
    }).done(function(data) {
        console.log(data);
        var objs = JSON.parse(data);
        var html = "";
        if(objs != null) {
            for(var i = 0; i < objs.length; i++) {
                var descricao = objs[i].descricao;
                var nome = objs[i].pessoa.nome;
                var sobrenome = objs[i].pessoa.sobrenome;
                var nota = objs[i].nota;
                var data_modificacao = objs[i].data_modificacao;
                
                html += '<li class="left clearfix">'
                            + '<span class="pull-left">'
                                + '<h1 style="margin-top: 0px">'+nota+'</h1>'
                            + '</span>'
                            + '<div class="chat-body clearfix">'
                                + '<div class="header">'
                                    + '<strong class="primary-font">'+nome+' '+sobrenome+'</strong>'
                                    + '<small class="pull-right text-muted">'
                                        + '<i class="fa fa-clock-o fa-fw"></i>'+dataFormatada(data_modificacao)
                                    + '</small>'
                                + '</div>'
                                + '<p style="word-wrap: break-word;">'+descricao+'</p>'
                            + '</div>'
                        + '</li>';
                
            }
        } else {
            
            html += '<li class="clearfix">'
                        + '<div class="chat-body clearfix">'
                            + '<p class="text-center"><i class="fa fa-ban fa-fw"></i> Nenhuma Avaliação</p>'
                        + '</div>'
                    + '</li>';
        }
        $("#caixa-avaliacao").html(html);        
        
    });
}