<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 31.10.2024
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Редактировать профиль питомца</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
        <script src="<c:url value='/js/scripts.js' />"></script>
    </head>
    <body>
        <div class="container">
            <h1>${pet.name}</h1>

            <form action="editPet" method="post">
                <input type="hidden" name="petId" value="${pet.id}">

                <div>
                    <label for="name">Имя: </label>
                    <input type="text" id="name" name="name" value="${pet.name}" required>
                </div>

                <div>
                    <label for="birthDate">Дата рождения: </label>
                    <input type="date" id="birthDate" name="birthDate" value="${pet.birthDate}" required>
                </div>

                <div>
                    <label for="species">Вид: </label>
                    <input type="text" id="species" name="species" value="${pet.species}" readonly>
                </div>

                <c:if test="${pet.species == 'собака'}">
                    <div>
                        <label for="breedId">Порода: </label>
                        <select id="breedId" name="breedId">
                            <c:forEach var="breed" items="${breeds}">
                                <option value="${breed.id}"
                                    <c:if test="${pet.breed != null && pet.breed.id == breed.id}">selected</c:if>>
                                    ${breed.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <button type="submit">Сохранить</button>
            </form>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>
