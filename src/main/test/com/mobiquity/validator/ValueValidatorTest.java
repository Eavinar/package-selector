package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ValueValidatorTest {

    private ThingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ValueValidator();
    }

    @Test
    public void validatorTest_whenValueIsCorrect() {
        Integer[] weight = {10};
        Integer[] value = {10};

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());
        assertDoesNotThrow(() -> validator.validate(weight, value, wrapper));
    }

    @Test
    public void validatorTest_throwException_whenValueExceedsLimit() {
        Integer[] weight = {10};
        Integer[] value = {101};

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());

        Exception exception = assertThrows(APIException.class, () -> validator.validate(weight, value, wrapper));

        String expectedMessage = String.format("Value limit exceeded. Expected <= %s", Constants.MAX_PRICE);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}