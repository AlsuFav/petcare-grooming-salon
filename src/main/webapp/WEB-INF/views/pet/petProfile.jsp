<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 31.10.2024
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Профиль питомца</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>${pet.name}</h1>

            <hr><br>

            <h2>Данные о питомце</h2>

            <p>Дата рождения: ${pet.getBirthDateFormatted()}</p>
            <p>Вид: ${pet.species}</p>
            <c:if test="${pet.species == 'собака'}">
                <p>Порода: ${pet.breed.name}</p>
            </c:if>

            <br>
            <p><b><a href="editPet?petId=${pet.id}">Редактировать профиль питомца</a></b></p>


            <h2>Предстоящие записи</h2>

            <c:if test="${not empty upcomingAppointments}">
                <c:forEach var="appointment" items="${upcomingAppointments}">
                    <p><b>${appointment.getDateAndTime()}</b></p>
                    <p>Услуга: <a href="services">${appointment.service.name}</a></p>
                    <p>Цена: ${appointment.price}₽</p>
                    <p><a href="appointmentDetails?appointmentId=${appointment.id}">Детали записи</a></p>
                </c:forEach>
            </c:if>

            <c:if test="${empty upcomingAppointments}">
                <p>Нет предстоящих записей.</p>
            </c:if>

            <br>
            <p><b><a href="selectAppointmentOptions">Записать питомца на груминг</a></b></p>

            <br>
            <form action="deletePet" method="post" style="display:inline;">
                <input type="hidden" name="petId" value="${pet.id}">
                <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить этого питомца?')">Удалить профиль питомца</button>
            </form>

            <c:if test="${param.error == 'cancel_appointments_first'}">
                <p style="color:red;">Невозможно удалить питомца. Сначала отмените все предстоящие записи.</p>
            </c:if>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>