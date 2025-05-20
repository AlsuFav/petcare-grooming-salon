<%--
  Created by IntelliJ IDEA.
  User: Alsu
  Date: 08.12.2024
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Сезонный уход за питомцами</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
</head>
<body>
<div class="container">
    <header>
        <h1>Сезонный уход за питомцами</h1>
    </header>

    <hr><br>

    <section>
        <h2>Зимний уход</h2>
        <p>
            Зимой лапы питомца страдают от реагентов и соли. Советы по уходу:
        </p>
        <ul>
            <li>Промывайте лапы теплой водой после каждой прогулки.</li>
            <li>Наносите защитный воск перед выходом на улицу.</li>
            <li>Не стригите шерсть слишком коротко, чтобы питомцу не было холодно.</li>
        </ul>
    </section>
    <section>
        <h2>Летний уход</h2>
        <p>
            Летом питомцам особенно важно избегать перегрева:
        </p>
        <ul>
            <li>Купайте питомца чаще, чтобы удалить пыль и пот.</li>
            <li>Убедитесь, что у животного есть доступ к чистой воде и тенистому месту.</li>
            <li>Не гуляйте в самое жаркое время дня, чтобы избежать ожогов лап на горячем асфальте.</li>
        </ul>
    </section>
    <section>
        <h2>Уход в период линьки</h2>
        <p>
            Линька происходит весной и осенью, когда питомцы теряют старую шерсть:
        </p>
        <ul>
            <li>Вычесывайте питомца ежедневно, чтобы уменьшить количество шерсти в доме.</li>
            <li>Используйте шампуни, которые стимулируют линьку, чтобы ускорить процесс.</li>
            <li>Посетите груминг-салон для профессионального ухода.</li>
        </ul>
    </section>


    <br>
    <h3><a href="${pageContext.request.contextPath}">Вернуться на главную страницу</a></h3>

</div>
</body>
</html>