<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 30.10.2024
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Подтверждите запись</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Подтверждение записи</h1>

            <p>Питомец: ${selectedPet.name}</p>
            <p>Услуга: ${selectedService.name}</p>
            <p>Время записи: ${selectedTimeSlot.getDateAndTime()}</p>
            <p>Стоимость: ${price}₽ </p>

            <br>
            <form method="post" action="confirmAppointment" class="inline_form">
                <input type="hidden" name="price" value="${price}">
                <button type="submit">Подтвердить</button>
            </form>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>
