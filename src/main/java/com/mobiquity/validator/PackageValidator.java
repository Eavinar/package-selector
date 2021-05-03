package com.mobiquity.validator;

import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIException;

/**
 * Main Validation Interface. Used as Composite Pattern Interface.
 * All of the implementations of current interface will be ran against {@link PackageWrapper}
 */
public interface PackageValidator {

    /**
     * @return name of Validator
     */
    String name();

    /**
     * @param weight array of all weights
     * @param value array of all values.
     * @param wrapper for the list of {@link com.mobiquity.dto.PackageDTO}. Keeps also maxWeight.
     * @throws APIException when validation of weight&value&currency fails.
     */
    void validate(Integer[] weight, Integer[] value, PackageWrapper wrapper) throws APIException;
}
