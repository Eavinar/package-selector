package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SameIdValidatorTest {
    private ThingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SameIdValidator();
    }

    @Test
    public void validatorTest_whenIdsUnique() {
        Integer[] weight = new Integer[1];
        Integer[] value = new Integer[1];
        String[] thingDetails1 = {"1", "10", "$20"};
        String[] thingDetails2 = {"2", "10", "$20"};
        ThingDTO thing1 = new ThingDTO.ThingDTOBuilder().build(thingDetails1);
        ThingDTO thing2 = new ThingDTO.ThingDTOBuilder().build(thingDetails2);

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Arrays.asList(thing1, thing2));
        assertDoesNotThrow(() -> validator.validate(weight, value, wrapper));
    }

    @Test
    public void validatorTest_throwException_whenIdsNotUnique() {
        Integer[] weight = new Integer[1];
        Integer[] value = new Integer[1];
        String[] thingDetails1 = {"1", "10", "$20"};
        String[] thingDetails2 = {"1", "10", "#20"};
        ThingDTO thing1 = new ThingDTO.ThingDTOBuilder().build(thingDetails1);
        ThingDTO thing2 = new ThingDTO.ThingDTOBuilder().build(thingDetails2);

        ThingsWrapper wrapper = new ThingsWrapper(Constants.MAX_WEIGHT, Arrays.asList(thing1, thing2));

        Exception exception = assertThrows(APIException.class, () -> validator.validate(weight, value, wrapper));

        String expectedMessage = "Duplicate index";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}