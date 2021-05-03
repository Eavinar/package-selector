package com.mobiquity.factory;

import com.mobiquity.dto.ThingDTO;

import java.util.Comparator;

public class ThingSortFactory {
    public static Comparator<ThingDTO> factory(String sortBy) {
        switch (sortBy) {
            case "ID" : return Comparator.comparing(ThingDTO::getId);
            case "PRICE" : return Comparator.comparing(ThingDTO::getPrice);
            case "ID_REVERSE" : return Comparator.comparing(ThingDTO::getId).reversed();
            case "PRICE_REVERSE" : return Comparator.comparing(ThingDTO::getPrice).reversed();
            case "WEIGHT_REVERSED" : return Comparator.comparing(ThingDTO::getWeight).reversed();
            case "WEIGHT" :
            default: return Comparator.comparing(ThingDTO::getWeight);
        }
    }
}
