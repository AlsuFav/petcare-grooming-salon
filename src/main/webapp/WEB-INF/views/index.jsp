<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Care - Главная страница</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
    <link rel="stylesheet" href="<c:url value='/css/mainPage.css' />">
    <script src="<c:url value='/js/mainPage.js' />"></script>
</head>
<body>
<div class="container">
    <header>
        <img src="<c:url value='/assets/logotype.jpg' />" alt="Pet Care Logo" class="logo">
        <h1>Pet Care</h1>
        <h4>Dog and Cat Grooming Salon</h4>
    </header>

    <br>
    <form action="login" method="get" class="inline_form">
        <button type="submit">Вход</button>
    </form>
    <form action="register" method="get" class="inline_form">
        <button type="submit">Регистрация</button>
    </form>

    <br>
    <section class="gallery">
        <div id="sliderVertical" class="sliderVertical">
            <img src="<c:url value='/assets/pet/grooming1.jpg' />" alt="Grooming" class="slide">
            <img src="<c:url value='/assets/pet/hygiene1.jpg' />" alt="Hygiene" class="slide">
            <img src="<c:url value='/assets/pet/grooming2.jpg' />" alt="Grooming" class="slide">
            <img src="<c:url value='/assets/pet/hygiene2.jpg' />" alt="Hygiene" class="slide">
            <img src="<c:url value='/assets/pet/coloring.jpg' />" alt="Grooming + Coloring" class="slide">
            <img src="<c:url value='/assets/pet/grooming3.jpg' />" alt="Grooming" class="slide">
        </div>
        <div class="slider-captions" id="captionsVertical">
            <div class="caption">Grooming</div>
            <div class="caption">Hygiene</div>
            <div class="caption">Grooming</div>
            <div class="caption">Hygiene</div>
            <div class="caption">Grooming + Coloring</div>
            <div class="caption">Grooming</div>
        </div>
    </section>

    <br>
    <h2>Список услуг</h2>

    <c:if test="${not empty services}">
        <c:forEach var="service" items="${services}">
            <h3>${service.name}</h3>
            <p><i>${service.description} </i></p>
        </c:forEach>

        <br>
        <h4><a href="services">Список услуг с ценами</a></h4>
        <form action="selectAppointmentOptions" method="get" class="inline_form">
            <button type="submit">Записать питомца на груминг</button>
        </form>
    </c:if>

    <c:if test="${empty services}">
        <p>К сожалению, список услуг пуст :(</p>
    </c:if>

    <br>
    <h2>Наш салон</h2>

    <section class="gallery">
        <div id="sliderHorizontal" class="sliderHorizontal">
            <img src="<c:url value='/assets/salon/combZone.jpg' />" alt="Зона стрижки" class="slide">
            <img src="<c:url value='/assets/salon/washZone.jpg' />" alt="Зона мытья" class="slide">
            <img src="<c:url value='/assets/salon/receptionZone.jpg' />" alt="Ресепшн" class="slide">
        </div>
        <div class="slider-captions" id="captionsHorizontal">
            <div class="caption">Зона стрижки</div>
            <div class="caption">Зона мытья</div>
            <div class="caption">Ресепшн</div>
        </div>
    </section>

    <br>
    <h2>Рекомендуем ознакомиться со статьями</h2>
    <p><a href="<c:url value='/articles/reasonsForGrooming.jsp' />">Почему необходимо водить питомца на груминг?</a></p>
    <p><a href="<c:url value='/articles/advicesBetweenGrooming.jsp' />">Уход за питомцем между визитами в груминг-салон</a></p>
    <p><a href="<c:url value='/articles/seasonalCare.jsp' />">Сезонный уход за питомцами</a></p>

    <br>
    <section class="faq">
        <h2>Часто задаваемые вопросы</h2>
        <div class="question" onclick="toggleAnswer('q1')">
            <p>Сколько времени занимают услуги?</p>
            <div id="q1" class="answer">
                <p>Услуга занимает от 1,5 до 2 часов в зависимости от вида услуги, породы и состояния шерсти.</p>
            </div>
        </div>

        <div class="question" onclick="toggleAnswer('q2')">
            <p>Можно ли оставлять питомца в салоне одного?</p>
            <div id="q2" class="answer">
                <p>Да, питомцы могут оставаться в салоне одни, мы позвоним вам за 15 минут до окончания процедуры.</p>
            </div>
        </div>

        <div class="question" onclick="toggleAnswer('q3')">
            <p>Какие документы нужны?</p>
            <div id="q3" class="answer">
                <p>Нужен ветеринарный паспорт с актуальными прививками.</p>
            </div>
        </div>
    </section>

    <br>
    <p><b>Остались вопросы?</b> Свяжитель с нами по электронной почте <a href="mailto:alsuf05@bk.ru">petcare@mail.ru</a></p>

</div>
</body>
</html>