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