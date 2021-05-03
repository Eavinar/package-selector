package com.mobiquity.helper;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.PackageDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class for manipulating with list of {@link PackageDTO}.
 * Class helps to get weights array, values array and etc.
 */
public class PackageHelper {
    public static Integer[] getValues(List<PackageDTO> packageDTOList) {
        return packageDTOList.stream()
                .map(PackageDTO::getPrice)
                .toArray(Integer[]::new);
    }

    public static Integer[] getWeights(List<PackageDTO> packageDTOList) {
        return packageDTOList.stream().map(PackageDTO::getWeight)
                .map(p -> Double.valueOf(p * Constants.HUNDRED).intValue())
                .toArray(Integer[]::new);
    }

    public static List<PackageDTO> getPackageDTOS(List<PackageDTO> packageDTOList, List<Integer> indexes) {
        List<PackageDTO> packedDTOs = new ArrayList<>();

        for (Integer val : indexes) {
            PackageDTO packageDTO = packageDTOList.get(val - 1);
            packedDTOs.add(packageDTO);
        }
        return packedDTOs;
    }

    public static String getString(List<PackageDTO> packageDTOList, List<Integer> indexes) {
        List<PackageDTO> packedDTOs = PackageHelper.getPackageDTOS(packageDTOList, indexes);

        String result = "-";
        if (!packedDTOs.isEmpty()) {
            result = packedDTOs.stream()
                    .map(PackageDTO::getId)
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.joining(","));

        }
        return result;
    }
}
