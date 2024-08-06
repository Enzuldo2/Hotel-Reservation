<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            width: 45%;
            margin: 5%;
            padding: 5%;
            border: 4px solid black;
            border-radius: 25px;
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

        .conteiner-central {
            text-align: center;
            margin: 5%;
            padding: 3%;
            border: 4px solid black;
            border-radius: 25px;
            display: grid;
            grid-auto-flow: row;
            place-items: center;
        }

        .escolha {
            display: grid;
            grid-auto-flow: column;
            width: 60%;
            margin: 5%;
            margin-bottom: 10%;
        }

        .cliente, .profissional {
            width: 80%;
            height: 80%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>
</head>
<body>
    <fmt:bundle basename="messages">
        <header>
            <div class="titulo">
                <h1>BuscarX</h1>
            </div>
            <a href="/AgendarConsultas/index.jsp" class="btn voltar">
                <fmt:message key="voltar"/>
            </a>
        </header>

        <div class="conteiner-central">
            <div>
                <h1>
                    <fmt:message key="msg_cadastro"/>
                </h1>
            </div>
            <div class="escolha">
                <a href="/AgendarConsultas/cadastro/cliente.jsp" class="btn cliente">
                    <fmt:message key="cliente"/>
                </a>
                <a href="/AgendarConsultas/cadastro/profissional.jsp" class="btn profissional">
                    <fmt:message key="profissional"/>
                </a>
            </div>
        </div>
    </fmt:bundle>
</body>
</html>

