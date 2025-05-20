<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 31.10.2024
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Детали записи</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Детали записи</h1>

            <p>Дата: ${appointment.getDateAndTime()}</p>
            <p>Питомец: <a href="petProfile?petId=${appointment.pet.id}">${appointment.pet.name}</a></p>
            <p>Услуга: <a href="services">${appointment.service.name}</a></p>
            <p>Грумер: ${appointment.groomer.firstName}</p>
            <p>Цена: ${appointment.price}₽</p>

            <form action="cancelAppointment" method="post" class="inline_form">
                <input type="hidden" name="appointmentId" value="${appointment.id}">
                <button type="submit" onclick="return confirm('Вы уверены, что хотите отменить запись?')">Отменить запись</button>
            </form>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>