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
        .btn {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
            width: 45%;
            text-align: center;
            font-weight: bold;
            color: #000000;
            text-decoration: none; 
            transition: background-color 0.7s ease, color 0.4s ease;
        }
        .btn:hover {
            color: #ffffff;
            background-color: #000000;
        }
        .formulario {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }
        h3 {
            margin-top: -5%;
        }
        label {
            font-weight: bold;
        }
        input, select {
            width: 85%;
            margin: 2%;
            padding: 1%;
            background-color: white;
            border: 4px solid black;
            border-radius: 10px;
        }
        .confirmar {
            margin-left: 30%;
            margin-right: 30%;
            background-color: white;
            font-weight: bold;
            cursor: pointer;
            width: 35%;
            transition: border-color 0.3s ease, color 0.4s ease;
        }
        .confirmar:hover {
            background-color: #28e673;
            border-color: white;
        }
    </style>
</head>
<body>
    <c:if test="${not empty sessionScope.erroNovoUsuario}">
        <script>
            alert("${sessionScope.erroNovoUsuario}");
        </script>
        <c:remove var="erroNovoUsuario" scope="session"/>
    </c:if>

    <fmt:bundle basename="messages">
        <header>
            <div class="titulo">
                <h1>BuscarX</h1>
            </div>
            <a href="/AgendarConsultas/cadastro/cadastro.jsp" class="btn voltar">
                <fmt:message key="voltar"/>
            </a>
        </header>
        <div class="formulario">
            <form id="form" action="/AgendarConsultas/cliente" method="post">
                <h3>
                    <fmt:message key="cliente" />
                </h3>

                <label for="nome">
                    <fmt:message key="nome"/>
                </label> <br>
                <input
                    type="text"
                    id="nome"
                    name="nome"
                    value="${sessionScope.nome != null ? sessionScope.nome : ''}"
                    required
                ><br>

                <label for="email">
                    <fmt:message key="email"/>
                </label> <br>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value="${sessionScope.email != null ? sessionScope.email : ''}"
                    required
                ><br>

                <label for="senha">
                    <fmt:message key="senha"/>
                </label> <br>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                    value="${sessionScope.senha != null ? sessionScope.senha : ''}"
                    required
                ><br>

                <label for="cpf">
                    <fmt:message key="cpf"/>
                </label> <br>
                <input
                    type="number"
                    id="cpf"
                    name="cpf"
                    value="${sessionScope.cpf != null ? sessionScope.cpf : ''}"
                    required
                    maxlength="11"
                ><br>

                <label for="sexo">
                    <fmt:message key="sexo"/>
                </label><br>

                <select name="sexo" id="opcao">
                    <option value="masculino" ${'masculino' eq sessionScope.sexoString ? 'selected' : ''}>
                        <fmt:message key="masculino"/>
                    </option>
                    <option value="feminino" ${'feminino' eq sessionScope.sexoString ? 'selected' : ''}>
                        <fmt:message key="feminino"/>
                    </option>
                    <option value="outro" ${'outro' eq sessionScope.sexoString ? 'selected' : ''}>
                        <fmt:message key="outro"/>
                    </option>
                </select> <br>

                <label for="data de nascimento">
                    <fmt:message key="nascimento"/>
                </label> <br>
                <input
                    type="date"
                    id="nascimento"
                    name="nascimento"
                    required
                    value="${sessionScope.dataNascimentoString != null ? sessionScope.dataNascimentoString : ''}"
                ><br>

                <input
                    type="submit"
                    class="confirmar"
                    value="<fmt:message key='confirmar'/>"
                >
            </form>
        </div>
    </fmt:bundle>
    <script>
    </scirpt>
</body>
</html>

