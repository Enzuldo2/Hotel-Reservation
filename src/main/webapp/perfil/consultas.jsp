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

        header{
            display: grid;
            grid-auto-flow : column;
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

        .tabela{
            padding: 0% 0;
            margin: 5px;
            padding-top: 0%;
            text-align: center;
            border: solid;
            border-radius: 15px;

        }
        .botao{
            width: 100%;
            border: none;
            font-weight: bold;
            text-decoration: none;
            color: black;
            background-color: white;
            cursor: pointer;
            transition: background-color 0.7s ease, color 0.4s ease;
        }
        .botao:hover .nome, .botao:hover .especialidade{
            background-color: black;
            color: white;
        }

        hr {
            margin: 0%;
            border: 1px solid black;
        }

        .celula {
            display: grid;
            grid-auto-flow: column;
            padding: 5%;
        }

        .ponta {
            font-size: 23px;
            font-weight: bold;
        }

        .meio {
            font-size: 15px;
        }

        .seta {
            display: grid;
            grid-template-columns: 100% 1%;
            align-items: center;

        }
        .triangulo {
          width: 0; 
          height: 0; 
          border-top: 10px solid transparent;
          border-bottom: 10px solid transparent;
          border-left: 10px solid black;
        }

    </style>
</head>
<body>
    <c:set var="cliente" value="${sessionScope.cliente}" />
    <c:set var="profissional" value="${sessionScope.profissional}" />
    <c:set var="listaAgendamentoPorUsuario" value="${sessionScope.listaAgendamentoPorUsuario}" />

    <c:if test="${not empty sessionScope.semConsultas}">
        <script>
            alert("${sessionScope.semConsultas}");
        </script>
        <c:remove var="semConsultas" scope="session"/>
    </c:if>

    <fmt:bundle basename="messages">
    <header>
        <div class="titulo">BuscarX</div>

        <div class="usuario">
            <c:choose>
                <c:when test="${cliente != null}">
                    <a href="/AgendarConsultas/perfil/usuario.jsp" class="btn cliente">
                        <fmt:message key="BoasVindas" /> ${cliente.nome}
                    </a>
                </c:when>
                <c:when test="${profissional != null}">
                    <a href="/AgendarConsultas/perfil/usuario.jsp" class="btn profissional">
                        <fmt:message key="BoasVindas" /> ${profissional.nome}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/AgendarConsultas/login/login.jsp" class="btn login">
                        <fmt:message key="entrar"/>
                    </a>
                    <a href="/AgendarConsultas/cadastro/cadastro.jsp" class="btn cadastro">
                        <fmt:message key="cadastro"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
    <main>
        <h1 align="center"> Lista de Consultas </h1>
            <div class="tabela">
                <c:forEach var="agendamento" items="${listaAgendamentoPorUsuario}">
                    <div class="celula">
                        <div class="ponta">
                            ${agendamento.nomeCliente}
                        </div>
                        <div class="meio">
                            ${agendamento.horario} |
                            ${agendamento.data}
                            <div class="seta">
                                <div class="linha"> <hr> </div>
                                <div class="triangulo"> </div>
                            </div>
                        </div>
                        <div class="ponta">
                            ${agendamento.nomeProfissional}
                        </div>
                    </div>
                    <hr>
                </c:forEach>
            </div>
    </main>
    </fmt:bundle>
    <script>
        window.onload = function() {
        }
    </script>
</body>
</html>

