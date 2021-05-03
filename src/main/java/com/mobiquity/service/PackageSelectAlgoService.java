package com.mobiquity.service;

import com.mobiquity.dto.PackageWrapper;

/**
 * Interface is responsible for running algorithm in order to identify required packages.
 */
public interface PackageSelectAlgoService {

    /**
     * @param weight array of weights
     * @param value array of values
     * @param wrapper wrapper class of {@link com.mobiquity.dto.PackageDTO}. Also keeps maxWeight.
     * @return {@link StringBuilder} of all found packages to be included.
     */
    StringBuilder select(Integer[] weight, Integer[] value, PackageWrapper wrapper);
}
