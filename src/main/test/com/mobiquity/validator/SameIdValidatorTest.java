package com.mobiquity.validator;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.PackageDTO;
import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SameIdValidatorTest {
    private PackageValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SameIdValidator();
    }

    @Test
    public void validatorTest_whenIdsUnique() {
        Integer[] weight = new Integer[1];
        Integer[] value = new Integer[1];
        String[] packageDetails1 = {"1", "10", "$20"};
        String[] packageDetails2 = {"2", "10", "$20"};
        PackageDTO package1 = new PackageDTO.PackageDTOBuilder().build(packageDetails1);
        PackageDTO package2 = new PackageDTO.PackageDTOBuilder().build(packageDetails2);

        PackageWrapper wrapper = new PackageWrapper(Constants.MAX_WEIGHT, Arrays.asList(package1, package2));
        assertDoesNotThrow(() -> validator.validate(weight, value, wrapper));
    }

    @Test
    public void validatorTest_throwException_whenIdsNotUnique() {
        Integer[] weight = new Integer[1];
        Integer[] value = new Integer[1];
        String[] packageDetails1 = {"1", "10", "$20"};
        String[] packageDetails2 = {"1", "10", "#20"};
        PackageDTO package1 = new PackageDTO.PackageDTOBuilder().build(packageDetails1);
        PackageDTO package2 = new PackageDTO.PackageDTOBuilder().build(packageDetails2);

        PackageWrapper wrapper = new PackageWrapper(Constants.MAX_WEIGHT, Arrays.asList(package1, package2));

        Exception exception = assertThrows(APIException.class, () -> validator.validate(weight, value, wrapper));

        String expectedMessage = "Duplicate index";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}