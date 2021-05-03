package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SizeValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(SizeValidator.class);
    /**
     * @inheritDoc
     * @return
     */
    @Override
    public String name() {
        return "Equal size of weight and value arrays Validator";
    }

    /**
     * Method validates whether the size of weight and value arrays are identical.
     * Please check {@link Constants}
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when validation of array sizes fails.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        if (weight.length != value.length) {
            log.error("Weight and Value size is not identical. {} against {}. Status: {}", weight.length, value.length, ValidatationStatus.FAIL);
            throw new APIException("Weight and Value size is not identical");
        }
    }
}
