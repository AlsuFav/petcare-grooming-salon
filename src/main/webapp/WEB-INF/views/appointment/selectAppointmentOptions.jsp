<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 30.10.2024
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Выбор питомца и услуги</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Выберите питомца и услугу</h1>

            <c:if test="${not empty pets}">

                <form method="post" action="selectAppointmentOptions">
                    <label for="petId">Питомец: </label>
                    <select name="petId" id="petId" required>
                        <c:forEach var="pet" items="${pets}">
                            <option value="${pet.id}">${pet.name} (${pet.species})</option>
                        </c:forEach>
                    </select>

                    <label for="serviceId">Услуга: </label>
                    <select name="serviceId" id="serviceId" required>
                        <c:forEach var="service" items="${services}">
                            <option value="${service.id}">${service.name}</option>
                        </c:forEach>
                    </select>

                    <button type="submit">Далее</button>
                </form>
            </c:if>

            <c:if test="${empty pets}">
                <p><b>Список питомцев пуст</b></p>
                <form action="addPet" method="get" class="inline_form">
                    <button type="submit">Добавить питомца</button>
                </form>
            </c:if>

            <br>
            <h3><a href="services">Посмотреть список услуг</a></h3>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>