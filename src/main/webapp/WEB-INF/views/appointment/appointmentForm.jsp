<%--
  Created by IntelliJ IDEA.
  User: alsu
  Date: 20.05.2025
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Запись на услугу</title>
  <link rel="stylesheet" href="<c:url value='/css/styles.css' />">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
  <h1>Запись на услугу</h1>

  <c:if test="${not empty pets}">
    <form id="appointmentForm">
      <!-- Питомец -->
      <div class="form-group">
        <label for="petSelect">Питомец:</label>
        <select id="petSelect" class="form-control" required>
          <option value="">Выберите питомца</option>
          <c:forEach var="pet" items="${pets}">
            <option value="${pet.id}">${pet.name} (${pet.species})</option>
          </c:forEach>
        </select>
      </div>

      <div class="form-group">
        <label for="serviceSelect">Услуга:</label>
        <select id="serviceSelect" class="form-control" required disabled>
          <option value="">Сначала выберите питомца</option>
        </select>
      </div>

      <div class="form-group">
        <label for="timeSlotSelect">Время записи:</label>
        <select id="timeSlotSelect" class="form-control" required disabled>
          <option value="">Загрузка...</option>
        </select>
      </div>

      <div id="priceContainer" style="display: none;">
        <p>Стоимость: <span id="priceValue"></span>₽</p>
      </div>

      <button type="submit" class="btn btn-primary">Подтвердить запись</button>
    </form>
  </c:if>

  <c:if test="${empty pets}">
    <p><b>У вас пока нет питомцев.</b></p>
    <form action="addPet" method="get" class="inline_form">
      <button type="submit">Добавить питомца</button>
    </form>
    <form action="clientProfile" method="get" class="inline_form">
      <button type="submit">Вернуться в профиль</button>
    </form>
  </c:if>
</div>

<script>
  $(document).ready(function() {
    loadTimeSlots();

    $('#petSelect').change(function() {
      const petId = $(this).val();
      if (petId) {
        loadServicesForPet(petId);
        $('#serviceSelect').prop('disabled', false);
      } else {
        $('#serviceSelect').html('<option value="">Сначала выберите питомца</option>')
                .prop('disabled', true);
        $('#timeSlotSelect').prop('disabled', true);
        $('#priceContainer').hide();
      }
    });

    $('#serviceSelect').change(function() {
      const serviceId = $(this).val();
      const petId = $('#petSelect').val();

      if (serviceId && petId) {
        calculatePrice(petId, serviceId);
      } else {
        $('#priceContainer').hide();
      }
    });

    $('#appointmentForm').submit(function(e) {
      e.preventDefault();

      const petId = $('#petSelect').val();
      const serviceId = $('#serviceSelect').val();
      const timeSlotId = $('#timeSlotSelect').val();

      if (!petId || !serviceId || !timeSlotId) {
        alert('Пожалуйста, заполните все поля');
        return;
      }

      $.post({
        url: '/createAppointment',
        data: {
          petId: petId,
          serviceId: serviceId,
          timeSlotId: timeSlotId
        },
        success: function() {
          window.location.href = '/clientProfile';
        },
        error: function() {
          alert('Ошибка при создании записи');
        }
      });
    });
  });

  function loadServicesForPet(petId) {
    $.get('/availableServices?petId=' + petId, function(services) {
      const $select = $('#serviceSelect');
      $select.empty();

      if (services.length === 0) {
        $select.append('<option value="">Нет доступных услуг</option>');
        $('#timeSlotSelect').prop('disabled', true);
        return;
      }

      $select.append('<option value="">Выберите услугу</option>');
      $.each(services, function(i, service) {
        $select.append($('<option>', {
          value: service.id,
          text: service.name
        }));
      });
    }).fail(function() {
      $('#serviceSelect').html('<option value="">Ошибка загрузки услуг</option>');
    });
  }

  function loadTimeSlots() {
    $.get('/availableTimeslots', function(data) {
      const $select = $('#timeSlotSelect');
      $select.empty().prop('disabled', false);

      $select.append('<option value="">Выберите время</option>');

      $.each(data, function(date, slots) {
        const $group = $('<optgroup>', { label: date });

        $.each(slots, function(i, slot) {
          $group.append($('<option>', {
            value: slot.id,
            text: slot.time + ' (Грумер: ' + slot.groomer.firstName + ')'
          }));
        });

        $select.append($group);
      });
    }).fail(function() {
      $('#timeSlotSelect').html('<option value="">Ошибка загрузки времени</option>');
    });
  }

  function calculatePrice(petId, serviceId) {
    $.get('/calculatePrice', {
      petId: petId,
      serviceId: serviceId
    }, function(price) {
      $('#priceValue').text(price);
      $('#priceContainer').show();
    }).fail(function() {
      $('#priceValue').text('не удалось рассчитать');
      $('#priceContainer').show();
    });
  }
</script>
</body>
</html>