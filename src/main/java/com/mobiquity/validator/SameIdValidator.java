package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.PackageDTO;
import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SameIdValidator implements PackageValidator {
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
     * @param wrapper for the list of {@link com.mobiquity.dto.PackageDTO}. Keeps also maxWeight.
     * @throws APIException when found duplicate ID.
     */
    @Override
    public void validate(Integer[] weight, Integer[] value, PackageWrapper wrapper) throws APIException {
        List<PackageDTO> packageDTOList = wrapper.getPackageDTOList();

        for (int i = 0; i < packageDTOList.size(); i++) {
            for (int j = 0; j < packageDTOList.size(); j++) {
                if (i != j && packageDTOList.get(i).equals(packageDTOList.get(j))) {
                    log.error("Duplicate index found: {}, {}. Status: {}", packageDTOList.get(i), packageDTOList.get(j), ValidatationStatus.FAIL);
                    throw new APIException("Duplicate index");
                }
            }
        }
    }
}
