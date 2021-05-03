package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaxWeightValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(MaxWeightValidator.class);

    /**
     * @inheritDoc
     * @return
     */
    @Override
    public String name() {
        return "MaxWeight Validator";
    }

    /**
     * Method validates correctness of the max weight. Max Weight has its own constraint.
     * Please check {@link Constants}
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when validation of max weight fails.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        if(wrapper.getMaxWeight() > Constants.MAX_WEIGHT) {
            log.error("Max weight is more than {}. Status: {}", Constants.MAX_WEIGHT / Constants.HUNDRED, ValidatationStatus.FAIL);
            throw new APIException(String.format("Max weight is more than %s", Constants.MAX_WEIGHT / Constants.HUNDRED));
        }
    }
}
