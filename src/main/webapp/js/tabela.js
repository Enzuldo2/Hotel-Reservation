document.addEventListener("DOMContentLoaded", function()
{
    function builder(path)
    {
        fetch(path)
        .then(function(response) 
        {
            return response.json();
        })
        .then(function(data) 
        {
            buildTable(data);
        })
        .catch(function(error)
        {
            console.error('Erro ao buscar os profissionais:', error);
        })
    }

    builder(`/AgendarConsultas/profissional?action=listarProfissionais`);

    const filtro = document.getElementById("filtro");

    filtro.addEventListener('input', function()
    {
        const pesquisa = filtro.value.toLowerCase().trim();

        if (pesquisa === '') 
        {
            builder(`/AgendarConsultas/profissional?action=listarProfissionais`);
        }
        else
        {
            builder(`/AgendarConsultas/profissional?action=filtrar&pesquisa=${pesquisa}`)
        }
    })

});

function buildTable(data) {
    let tabela = document.getElementById('tabela');
    tabela.innerHTML = ''; 

    data.forEach(profissional => {
        const link = document.createElement('a');
        link.className = 'botao';
        link.href = `/AgendarConsultas/agendamento?action=consultarPerfil&IdProfissionalEscolhido=${profissional.idUsuario}`;

        const nome = document.createElement('div');
        nome.className = 'nome';
        nome.textContent = profissional.nome;
        link.appendChild(nome);

        const especialidade = document.createElement('div');
        especialidade.className = 'especialidade';
        especialidade.textContent = profissional.especialidade;
        link.appendChild(especialidade);

        tabela.appendChild(link);

        const hr = document.createElement('hr');
        tabela.appendChild(hr);

        link.addEventListener('click', function(event) {
            console.log(testCliente);
            console.log(testProfissional);
            if (!testCliente && !testProfissional) {
                event.preventDefault();
                alert('Você precisa fazer login ou cadastro para acessar isso.');
            }

        });
    });
}


