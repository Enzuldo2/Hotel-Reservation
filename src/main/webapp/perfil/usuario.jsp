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
            padding: 0px 20px;
        }

        .botao{
            margin: 0px -20px;
            padding: 10%;
            font-size: 25px;
            border: none;
            font-weight: bold;
            color: black;
            background-color: white;
            cursor: pointer;
            text-align: center;
            transition: background-color 0.7s ease, color 0.4s ease;
        }
        .botao:hover{
            background-color: black;
            color: white;
        }

        hr {
            margin: 0px -20px;
            border: 1px solid black;
        }

        .atualizar{
            border-top-right-radius: 20px;
            border-top-left-radius: 20px;
        }
        .desconectar{
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
        }
    </style>
</head>
<body>
    <c:set var="cliente" value="${sessionScope.cliente}" />
    <c:set var="profissional" value="${sessionScope.profissional}" />
    <c:set var="listaProfissionais" value="${sessionScope.listaProfissionais}" />
    <c:set var="path" value="/AgendarConsultas/perfil/atualizar" />

    <c:if test="${not empty sessionScope.erroDeletar}">
        <script>
            alert('${sessionScope.erroDeletar}');
        </script>
        <c:remove var="erroDeletar" scope="session"/>
    </c:if>

    <fmt:bundle basename="messages">
    <header>
        <div class="titulo">BuscarX</div>

        <div class="usuario">
            <c:choose>
                <c:when test="${cliente != null}">
                    <a href="/AgendarConsultas" class="btn cliente" >
                        <fmt:message key="BoasVindas" /> ${cliente.nome}
                        <c:set var="path" value="${path}/cliente.jsp" />
                        <c:set var="idUsuario" value="${cliente.idUsuario}" />
                    </a>
                </c:when>
                <c:when test="${profissional != null}">
                    <a href="/AgendarConsultas" class="btn profissional">
                        <fmt:message key="BoasVindas" /> ${profissional.nome}
                        <c:set var="path" value="${path}/profissional.jsp" />
                        <c:set var="idUsuario" value="${profissional.idUsuario}" />
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/AgendarConsultas" class="btn login">
                        <fmt:message key="entrar"/>
                    </a>
                    <a href="/AgendarConsultas" class="btn cadastro">
                        <fmt:message key="cadastro"/>
                    </a>
                    <c:set var="path" value="/AgendarConsultas" />
                </c:otherwise>
            </c:choose>
        </div>
    </header>
    <main>
        <div class="opcao">
            <div class="botao atualizar" onclick="atualizarUsuario()"> 
                <input type="hidden" name="action" value="update">
                <fmt:message key="atualizar"/>
            </div>
            <hr>
            <div class="botao consultas" onclick="minhasConsultas()">
                <fmt:message key="consultas"/>
            </div>
            <hr>
            <div class="botao desconectar" onclick="desconectarUsuario()">
                <fmt:message key="desconectar"/>
            </div>
            <hr>
            <div class="botao deletar" onclick="deletarUsuario()">
                <fmt:message key="deletar"/>
            </div>
        </div>
    </main>
    </fmt:bundle>
    <script>
        function atualizarUsuario(){
         window.location.href = "${path}";
        }
        function desconectarUsuario(){
            window.location.href = "/AgendarConsultas/usuario?action=logout";
        }
        function minhasConsultas(){
            window.location.href = "/AgendarConsultas/agendamento?action=minhasConsultas&idUsuario=${idUsuario}";
        }
        function deletarUsuario(){
            const confirmacao = confirm("Tem certeza que quer excluir sua conta?");
            if (confirmacao){
                window.location.href = "/AgendarConsultas/usuario?action=deletarUsuario&idUsuario=${idUsuario}";
            }
        }
    </script>
</body>
</html>

