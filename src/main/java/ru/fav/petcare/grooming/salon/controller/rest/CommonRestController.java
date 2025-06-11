package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fav.petcare.grooming.salon.controller.response.FaqResponse;
import ru.fav.petcare.grooming.salon.controller.response.MapResponse;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Common info Controller", description = "Общая информация")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommonRestController {

    @Operation(summary = "Получить адрес груминг-салона",
            description = "Возвращает адрес и координаты груминг-салона")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных",
                    content = @Content(schema = @Schema(implementation = MapResponse.class))),
    })
    @GetMapping("/map")
    public ResponseEntity<MapResponse> findAddress() {
        MapResponse mapResponse = new MapResponse();
        mapResponse.setLatitude(55.817064);
        mapResponse.setLongitude(49.122505);
        mapResponse.setAddress("Ул. Cибгата Хакима, 17, Город Казань");
        return ResponseEntity.ok(mapResponse);
    }

    @Operation(summary = "Получить список часто задаваемых вопросов",
            description = "Возвращает информацию о текущем аутентифицированном клиенте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных",
                    content = @Content(schema = @Schema(implementation = MapResponse.class))),
    })
    @GetMapping("/faq")
    public ResponseEntity<List<FaqResponse>> findFaq() {
        List<FaqResponse> faqList = new ArrayList<>();

        faqList.add(new FaqResponse(
                1L,
                "Сколько времени занимают услуги?",
                "Услуга занимает от 1,5 до 2 часов в зависимости от вида услуги, породы и состояния шерсти."
        ));
        faqList.add(new FaqResponse(
                2L,
                "Можно ли оставлять питомца в салоне одного?",
                "Да, питомцы могут оставаться в салоне одни, мы позвоним вам за 15 минут до окончания процедуры."
        ));
        faqList.add(new FaqResponse(
                3L,
                "Какие документы нужны?",
                "Нужен ветеринарный паспорт с актуальными прививками."
        ));

        return ResponseEntity.ok(faqList);
    }
}
