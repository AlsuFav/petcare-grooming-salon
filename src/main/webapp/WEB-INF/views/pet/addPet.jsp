<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 21.11.2024
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Добавить питомца</title>
        <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
        <script src="<c:url value='/js/scripts.js' />"></script>
    </head>
    <body>
        <div class="container">
            <h1>Добавить питомца</h1>

            <form action="addPet" method="post">

                <input type="radio" id="cat" name="species" value="кошка" checked="checked" onclick="toggleBreedField()" required>
                <label for="cat">Кошка 🐈</label>

                <input type="radio" id="dog" name="species" value="собака" onclick="toggleBreedField()" required>
                <label for="dog">Собака 🐕</label>


                <div>
                    <label for="name">Имя: </label>
                    <input type="text" id="name" name="name" required>
                </div>

                <div>
                    <label for="birthDate">Дата рождения: </label>
                    <input type="date" id="birthDate" name="birthDate" required>
                </div>

                <div id="breedField" style="display: none;">
                    <label for="breedId">Порода: </label>
                    <select id="breedId" name="breedId">
                        <c:forEach var="breed" items="${breeds}">
                            <option value="${breed.id}">${breed.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit">Добавить</button>
            </form>

            <br>
            <h3><a href="clientProfile">Вернуться в профиль</a></h3>
        </div>
    </body>
</html>