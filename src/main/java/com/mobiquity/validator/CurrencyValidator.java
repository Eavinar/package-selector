package com.mobiquity.validator;

import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(CurrencyValidator.class);

    /**
     * @inheritDoc
     * @return
     */
    @Override
    public String name() {
        return "Currency Validator";
    }

    /**
     * Method validates correctness of the currency. Currency should be same of all things.
     * Currency also should exist.
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when validation of currency fails.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        Map<String, List<ThingDTO>> collect = wrapper.getThingDTOList().stream()
                .collect(Collectors.groupingBy(ThingDTO::getCurrency));
        if (collect.size() != 1) {
            log.error("Incorrect currency identified: {}. Status: {}", collect.keySet(), ValidatationStatus.FAIL);
            throw new APIException("Please check provided currencies");
        }
    }
}
