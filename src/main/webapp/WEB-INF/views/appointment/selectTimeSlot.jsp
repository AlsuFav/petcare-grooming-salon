<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 30.10.2024
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Выбор времени</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Выберите время записи</h1>

            <c:if test="${not empty availableSlots}">
                <form method="post" action="selectTimeSlot">
                    <c:forEach var="date" items="${availableSlots}">
                        <h3>${date.key}</h3>

                        <c:forEach var="slot" items="${date.value}">
                            <input type="radio" id="timeSlot_${slot.id}" name="timeSlotId" value="${slot.id}" required>
                            <label for="timeSlot_${slot.id}">${slot.getTime()} (Грумер: ${slot.groomer.firstName})</label>
                        </c:forEach>

                    </c:forEach>

                    <br>
                    <button type="submit">Далее</button>
                </form>
            </c:if>


            <c:if test="${empty availableSlots}">
                <p>К сожалению, свободных мест для записи не осталось :(</p>
            </c:if>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>