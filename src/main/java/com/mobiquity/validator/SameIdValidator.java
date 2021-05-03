package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SameIdValidator implements ThingValidator {
    private static final Logger log = LogManager.getLogger(SameIdValidator.class);
    /**
     * @inheritDoc
     * @return
     */
    @Override
    public String name() {
        return "Same Id Validator";
    }

    /**
     * Method validates whether all ids are unique.
     * Please check {@link Constants}
     *
     * @param weight  array of all weights
     * @param value   array of all values.
     * @param wrapper for the list of {@link ThingDTO}. Keeps also maxWeight.
     * @throws APIException when found duplicate ID.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, ThingsWrapper wrapper) throws APIException {
        List<ThingDTO> thingDTOList = wrapper.getThingDTOList();

        for (int i = 0; i < thingDTOList.size(); i++) {
            for (int j = 0; j < thingDTOList.size(); j++) {
                if (i != j && thingDTOList.get(i).equals(thingDTOList.get(j))) {
                    log.error("Duplicate index found: {}, {}. Status: {}", thingDTOList.get(i), thingDTOList.get(j), ValidatationStatus.FAIL);
                    throw new APIException("Duplicate index");
                }
            }
        }
    }
}
