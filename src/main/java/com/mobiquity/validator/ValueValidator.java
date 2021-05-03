package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValueValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(ValueValidator.class);
    /**
     * @inheritDoc
     * @return
     */
    @Override
    public String name() {
        return "Value Validator";
    }

    /**
     * Method validates whether value is within the limits. Price has its own constraint.
     * Please check {@link Constants}
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when validation of currency fails.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        for (Integer v : value) {
            if (v > Constants.MAX_PRICE) {
                log.error("Value limit exceeded. Expected <= {}. Status: {}", Constants.MAX_PRICE, ValidatationStatus.FAIL);
                throw new APIException(String.format("Value limit exceeded. Expected <= %s",
                        Constants.MAX_PRICE));
            }
        }
    }
}
