package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WeightValidatorTest {
    private ThingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new WeightValidator();
    }

    @Test
    public void validatorTest_whenWeightIsCorrect() {
        Integer[] weight = {10};
        Integer[] value = {10};

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());
        assertDoesNotThrow(() -> validator.validate(weight, value, wrapper));
    }

    @Test
    public void validatorTest_throwException_whenWeightExceedsLimit() {
        Integer[] weight = {101 * Constants.HUNDRED};
        Integer[] value = {10};

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT / Constants.HUNDRED, Collections.emptyList());

        Exception exception = assertThrows(APIException.class, () -> validator.validate(weight, value, wrapper));

        String expectedMessage = String.format("Weight limit exceeded. Expected <= %s", Constants.MAX_WEIGHT / Constants.HUNDRED);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}