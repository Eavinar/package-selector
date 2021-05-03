package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SizeValidatorTest {

    private ThingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SizeValidator();
    }

    @Test
    public void validatorTest_whenCurrencyIsSame() {
        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());
        assertDoesNotThrow(() -> validator.validate(new Integer[1], new Integer[1], wrapper));
    }

    @Test
    public void validatorTest_throwException_whenCurrencyDifferent() {
        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Collections.emptyList());

        Exception exception = assertThrows(APIException.class, () -> validator.validate(new Integer[1], new Integer[2], wrapper));

        String expectedMessage = "Weight and Value size is not identical";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}