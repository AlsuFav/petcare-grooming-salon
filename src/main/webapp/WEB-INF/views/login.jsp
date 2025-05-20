<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 30.10.2024
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Вход</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Вход</h1>

            <form action="login" method="post">
                <div>
                    <label for="phone">Телефон:</label>
                    <input type="tel" id="phone" name="phone" required>
                </div>

                <div>
                    <label for="password">Пароль:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit">Войти</button>
            </form>

            <c:if test="${error == 'invalid'}">
                <div class="error-message">
                    Неверный номер телефона или пароль. Попробуйте ещё раз.
                </div>
            </c:if>


            <h4>У вас нет профиля? <a href="register">Регистрация</a></h4>

        </div>
    </body>
</html>

