package ru.fav.petcare.grooming.salon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BreedTypeEnum {
    SMALL ("маленькая порода"),
    MEDIUM ("средняя порода"),
    LARGE ("большая порода");

    private final String title;
}
