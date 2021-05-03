package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeightValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(WeightValidator.class);

    /**
     * @return
     * @inheritDoc
     */
    @Override
    public String name() {
        return "Weight Validator";
    }

    /**
     * Method validates whether weight is within the limits. Weight has its own constraint.
     * Please check {@link Constants}
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when validation of currency fails.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        for (Integer w : weight) {
            if (w > Constants.MAX_WEIGHT) {
                log.error("Weight limit exceeded. Expected <= {}. Status: {}", Constants.MAX_WEIGHT / Constants.HUNDRED,
                        ValidatationStatus.FAIL);
                throw new APIException(String.format("Weight limit exceeded. Expected <= %s",
                        Constants.MAX_WEIGHT / Constants.HUNDRED));
            }
        }
    }
}
