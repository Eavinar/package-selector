package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MaxWeightValidatorTest {
    private ThingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MaxWeightValidator();
    }

    @Test
    public void validatorTest_whenMaxWeightIsCorrect() {
        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());
        assertDoesNotThrow(() -> validator.validate(new Integer[1], new Integer[1], wrapper));
    }

    @Test
    public void validatorTest_throwException_whenMaxWeightExceeds() {
        int maxWeight = Constants.MAX_WEIGHT + 1;
        ThingsWrapper wrapper = new ThingsWrapper(maxWeight, Collections.emptyList());

        Exception exception = assertThrows(APIException.class, () -> validator.validate(new Integer[1], new Integer[1], wrapper));

        String expectedMessage = String.format("Max weight is more than %s", Constants.MAX_WEIGHT / Constants.HUNDRED);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}