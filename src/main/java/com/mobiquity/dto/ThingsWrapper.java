package com.mobiquity.dto;

import java.util.Collections;
import java.util.List;

/**
 * Wrapper class for the list of {@link ThingDTO}
 */
public class ThingsWrapper {

    private final int maxWeight;
    private final List<ThingDTO> thingDTOList;

    public ThingsWrapper(final int maxWeight, final List<ThingDTO> thingDTOList) {
        this.maxWeight = maxWeight;
        this.thingDTOList = thingDTOList;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<ThingDTO> getThingDTOList() {
        return Collections.unmodifiableList(thingDTOList);
    }
}
