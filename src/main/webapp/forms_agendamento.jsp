<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>BuscarX</title>
    <style>
        body {
            font-family: "Comic Sans MS";
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }

        header {
            display: grid;
            grid-auto-flow: column;
            place-items: center;
        }
        .titulo {
            font-size: 50px;
            font-weight: bold;
        }

        .usuario {
            margin: 9%;
            border: 4px solid black;
            border-radius: 10px;
            padding: 5%;
            justify-items: center;
            display: grid;
            grid-auto-flow: column;
        }

        .btn {
            margin: 5px;
            border: 4px solid black;
            border-radius: 10px;
            padding: 1em;
            font-weight: bold;
            font-size: 20px;
            color: #000000;
            text-decoration: none;
            transition: background-color 0.7s ease, color 0.4s ease;
        }

        .btn:hover {
            color: #ffffff;
            background-color: #000000;
        }

        main {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }

        .tabela {
            padding: 0;
            margin: 5px;
            text-align: center;
            border: solid;
            border-radius: 15px;
        }
        .botao {
            width: 100%;
            border: none;
            font-weight: bold;
            text-decoration: none;
            color: black;
            background-color: white;
            cursor: pointer;
            transition: background-color 0.7s ease, color 0.4s ease;
        }
        .botao:hover .nome, .botao:hover .especialidade {
            background-color: black;
            color: white;
        }

        .nome {
            transition: background-color 0.7s ease, color 0.4s ease;
            font-size: 25px;
        }

        .especialidade {
            transition: background-color 0.7s ease, color 0.4s ease;
            font-size: 20px;
        }

        hr {
            margin: 0;
            border: 1px solid black;
        }

        label {
            font-size: 20px;
            font-weight: bold;
            display: block;
            text-align: left;
            margin: 10px 0 5px;
        }

        form{
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
        }

        input, select {
            width: 50%;
            margin: 2%;
            padding: 2%;
            background-color: white;
            border: 4px solid black;
            border-radius: 10px;
            text-align: center;
        }

        .confirmar {
            background-color: white;
            font-weight: bold;
            cursor: pointer;
            width: 35%;
            margin: 2%;
            padding: 2%;
            border: 4px solid black;
            border-radius: 10px;
            transition: border-color 0.3s ease, color 0.4s ease;
        }

        .confirmar:hover {
            background-color: #28e673;
            border-color: white;
        }

        h1 {
            font-size: 35px;
        }

        h2 {
            font-size: 32px;
        }

        h3 {
            font-size: 28px;
        }
    </style>
</head>
<body>
    <c:set var="cliente" value="${sessionScope.cliente}" />
    <c:set var="profissional" value="${sessionScope.profissional}" />
    <c:set var="profissionalEscolhido" value="${sessionScope.profissionalEscolhido}" />

    <c:if test="${not empty sessionScope.erroAgendamento}">
        <script>
            alert("${sessionScope.erroAgendamento}");
        </script>
        <c:remove var="erroAgendamento" scope="session"/>
    </c:if>

    <fmt:bundle basename="messages">
    <header>
        <div class="titulo">BuscarX</div>

        <div class="usuario">
            <c:choose>
                <c:when test="${cliente != null}">
                    <a href="/AgendarConsultas/" class="btn voltar">
                        <fmt:message key="voltar"/> 
                    </a>

                    <a href="/AgendarConsultas/perfil/usuario.jsp" class="btn cliente">
                        <fmt:message key="BoasVindas" /> ${cliente.nome}
                    </a>
                </c:when>
                <c:when test="${profissional != null}">
                    <a href="/AgendarConsultas/" class="btn voltar">
                        <fmt:message key="voltar"/> 
                    </a>

                    <a href="/AgendarConsultas/perfil/usuario.jsp" class="btn profissional">
                        <fmt:message key="BoasVindas" /> ${profissional.nome}
                    </a>

                </c:when>
                <c:otherwise>
                    <a href="/AgendarConsultas/login/login.jsp" class="btn login">
                        <fmt:message key="entrar" />
                    </a>
                    <a href="/AgendarConsultas/cadastro/cadastro.jsp" class="btn cadastro">
                        <fmt:message key="cadastro" />
                    </a>
                </c:otherwise>
            </c:choose>

        </div>
    </header>
    <main>
        <h1 align="center"> <fmt:message key="consulta" /> </h1>
        <h2 align="center"> <fmt:message key="nome" /> ${profissionalEscolhido.nome}</h2>
        <h3 align="center"> <fmt:message key="especialidade" /> ${profissionalEscolhido.especialidade}</h3>
        <form id="form" action="/AgendarConsultas/agendamento" method="post">
            <label for="data">
                <fmt:message key="data" />
            </label>
            <input
                type="date"
                id="data"
                name="data"
                value="${sessionScope.data != null ? sessionScope.data : ''}"
                required
            >

            <label for="horario">
                <fmt:message key="horario" />
            </label>
            <input
                type="time"
                id="horario"
                name="horario"
                step="1"
                value="${sessionScope.horario != null ? sessionScope.horario : ''}"
                required
            >

            <input
                type="submit"
                id = "confirmar"
                class="confirmar"
                value="<fmt:message key='confirmar' />"
            >
        </form>
    </main>
    </fmt:bundle>
    <script>
        var submit = document.getElementById("confirmar");
        submit.onclick = function(event){
            var profissional = ${profissional != null ? 'true' : 'false'};
            if (profissional)
            {
                event.preventDefault();
                alert('Profissional não pode fazer agendamento.');
            }
        }
    </script>
</body>
</html>

