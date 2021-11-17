package model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Beverage {
    private String name;
    private Map<String, Integer> ingredientQuantityMap;

    public Beverage(String name, Map<String, Integer> ingredientQuantityMap) {
        this.name = name;
        this.ingredientQuantityMap = ingredientQuantityMap;
    }
}
