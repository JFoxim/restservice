package ru.rinat.restservice.dict;

public enum Gender {
    MALE("муж"),
    FEMALE("жен");
    private final String name;
    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
