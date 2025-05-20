<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 28.11.2024
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Список услуг</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    </head>
    <body>
        <div class="container">
            <h1>Список услуг</h1>

            <c:if test="${not empty services}">
                <c:forEach var="service" items="${services}">
                    <h2>${service.name}</h2>
                    <p><i>${service.description} </i></p>
                    <p><b>Цены: </b></p>

                    <c:forEach var="servicePrice" items="${servicePrices}">
                        <c:if test="${servicePrice.service.id == service.id}">
                            <p>
                                    ${servicePrice.species}
                                <c:if test="${not empty servicePrice.breedType}">
                                    (${servicePrice.breedType.title})
                                </c:if>
                                : ${servicePrice.price}₽
                            </p>
                        </c:if>
                    </c:forEach>
                </c:forEach>

                <br>
                <form action="selectAppointmentOptions" method="get" class="inline_form">
                    <button type="submit">Записать питомца на груминг</button>
                </form>

            </c:if>


            <c:if test="${empty services}">
                <p>К сожалению, список услуг пуст :(</p>
            </c:if>


            <br>
            <h3><a href="${pageContext.request.contextPath}">Вернуться на главную страницу</a></h3>

        </div>
    </body>
</html>
