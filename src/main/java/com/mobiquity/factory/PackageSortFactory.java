package com.mobiquity.factory;

import com.mobiquity.dto.PackageDTO;

import java.util.Comparator;

public class PackageSortFactory {
    public static Comparator<PackageDTO> factory(String sortBy) {
        switch (sortBy) {
            case "PRICE" : return Comparator.comparing(PackageDTO::getPrice);
            case "WEIGHT" :
            default: return Comparator.comparing(PackageDTO::getWeight);
        }
    }
}
