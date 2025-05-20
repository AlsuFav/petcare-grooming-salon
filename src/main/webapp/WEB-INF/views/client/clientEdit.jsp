<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 08.12.2024
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container">
    <h1>Редактирование профиля</h1>
    <form action="editProfile" method="POST">
        <div>
            <label for="firstName">Имя:</label>
            <input type="text" id="firstName" name="firstName" value="${client.firstName}" required>
            <c:if test="${not empty errorFirstName}">
                <p style="color: red;">${errorFirstName}</p>
            </c:if>
        </div>

        <div>
            <label for="lastName">Фамилия:</label>
            <input type="text" id="lastName" name="lastName" value="${client.lastName}" required>
            <c:if test="${not empty errorLastName}">
                <p style="color: red;">${errorLastName}</p>
            </c:if>
        </div>

        <div>
            <label for="phone">Телефон:</label>
            <input type="text" id="phone" name="phone" value="${client.phone}" required>
            <c:if test="${not empty errorPhone}">
                <p style="color: red;">${errorPhone}</p>
            </c:if>
        </div>

        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${client.email}">
            <c:if test="${not empty errorEmail}">
                <p style="color: red;">${errorEmail}</p>
            </c:if>
        </div>

        <div>
            <label>
                <input type="checkbox" id="changePassword" name="changePassword" onchange="togglePasswordChange()">
                Изменить пароль
            </label>
        </div>

        <div id="passwordSection" style="display: none;">
            <div>
                <label for="currentPassword">Текущий пароль:</label>
                <input type="password" id="currentPassword" name="currentPassword">
                <c:if test="${not empty errorCurrentPassword}">
                    <p style="color: red;">${errorCurrentPassword}</p>
                </c:if>
            </div>
            <div>
                <label for="newPassword">Новый пароль:</label>
                <input type="password" id="newPassword" name="newPassword">
                <c:if test="${not empty errorNewPassword}">
                    <p style="color: red;">${errorNewPassword}</p>
                </c:if>
            </div>

            <div>
                <label for="confirmNewPassword">Подтвердите новый пароль:</label>
                <input type="password" id="confirmNewPassword" name="confirmNewPassword">
                <c:if test="${not empty errorConfirmNewPassword}">
                    <p style="color: red;">${errorConfirmNewPassword}</p>
                </c:if>
            </div>
        </div>

        <button type="submit">Сохранить</button>
    </form>

    <h4><a href="clientProfile">Вернуться в профиль</a>.</h4>
</div>
</body>
</html>