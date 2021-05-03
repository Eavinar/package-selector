package com.mobiquity.helper;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper class for manipulating with list of {@link ThingDTO}.
 * Class helps to get weights array, values array and etc.
 */
public class PackageHelper {
    public static Integer[] getValues(List<ThingDTO> thingDTOList) {
        return thingDTOList.stream()
                .map(ThingDTO::getPrice)
                .toArray(Integer[]::new);
    }

    public static Integer[] getWeights(List<ThingDTO> thingDTOList) {
        return thingDTOList.stream().map(ThingDTO::getWeight)
                .map(p -> Double.valueOf(p * Constants.HUNDRED).intValue())
                .toArray(Integer[]::new);
    }

    public static List<ThingDTO> getThingDTOs(List<ThingDTO> thingDTOList, List<Integer> indexes) {
        final List<ThingDTO> packedDTOs = new ArrayList<>();

        for (Integer val : indexes) {
            ThingDTO thingDTO = thingDTOList.get(val - 1);
            packedDTOs.add(thingDTO);
        }
        return Collections.unmodifiableList(packedDTOs);
    }

    public static String getString(final List<ThingDTO> thingDTOList, final List<Integer> indexes) {
        List<ThingDTO> packedDTOs = PackageHelper.getThingDTOs(thingDTOList, indexes);

        String result = "-";
        if (!packedDTOs.isEmpty()) {
            result = packedDTOs.stream()
                    .map(ThingDTO::getId)
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.joining(","));

        }
        return result;
    }
}
