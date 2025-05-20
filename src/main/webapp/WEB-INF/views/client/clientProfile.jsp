<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 30.10.2024
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Профиль</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>

        <div class="container">
            <h1>Добро пожаловать, ${client.firstName}!</h1>

            <hr><br>

            <h2>Контакты</h2>
            <p>Телефон: ${client.phone}</p>
            <c:if test="${not empty client.email}">
                <p>Почта: ${client.email}</p>
            </c:if>
            <c:if test="${empty client.email}">
                <p>Почта: нет</p>
            </c:if>

            <br>
            <p><b><a href="editProfile">Редактировать профиль</a></b></p>


            <h2>Питомцы</h2>
            <c:if test="${not empty pets}">
                <c:forEach var="pet" items="${pets}">
                    <p><a href="petProfile?petId=${pet.id}">${pet.name}</a></p>
                </c:forEach>
            </c:if>


            <c:if test="${empty pets}">
                <p>Список питомцев пуст</p>
            </c:if>

            <br>
            <p><b><a href="addPet">Добавить питомца</a></b></p>


            <h2>Предстоящие записи</h2>
            <c:if test="${not empty upcomingAppointments}">
                <c:forEach var="appointment" items="${upcomingAppointments}">
                    <p><b>${appointment.getDateAndTime()}</b></p>
                    <p>Питомец: <a href="petProfile?petId=${appointment.pet.id}">${appointment.pet.name}</a></p>
                    <p>Услуга: <a href="services">${appointment.service.name}</a></p>
                    <p>Цена: ${appointment.price}₽</p>
                    <p><a href="appointmentDetails?appointmentId=${appointment.id}">Детали записи</a></p>
                </c:forEach>
            </c:if>

            <c:if test="${empty upcomingAppointments}">
                <p>Нет предстоящих записей.</p>
            </c:if>

            <br>

            <p><b><a href="createAppointment">Записать питомца на груминг</a></b></p>


            <br>

            <form action="logout" method="get" class="inline_form">
                <button type="submit">Выйти из профиля</button>
            </form>

            <form action="deleteClient" method="post" class="inline_form">
                <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить профиль?')">Удалить профиль</button>
            </form>

            <c:if test="${error == 'cancel_appointments_first'}">
                <div class="error-message">
                    Невозможно удалить аккаунт. Сначала необходимо отменить все предстоящие записи.
                </div>
            </c:if>

            <br>
            <h3><a href="/">Вернуться на главную страницу</a></h3>
        </div>
    </body>
</html>