package com.mobiquity.service;

import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.dto.ThingDTO;

/**
 * Interface is responsible for running algorithm in order to identify required things.
 */
public interface ThingsSelectAlgoService {

    /**
     * @param weight array of weights
     * @param value array of values
     * @param wrapper wrapper class of {@link ThingDTO}. Also keeps maxWeight.
     * @return {@link StringBuilder} of all found things to be included.
     */
    StringBuilder select(Integer[] weight, Integer[] value, ThingsWrapper wrapper);
}
