package com.mobiquity.transformer;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.PackageDTO;
import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringToPackageDTOTransformerTest {

    private StringToPackageDTOTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new StringToPackageDTOTransformer();
    }

    @Test
    public void testTransformer() {
        String input = "50 : (1,50,€405) (2,10,€45)";
        PackageWrapper transform = transformer.transform(input);

        assertEquals(50 * Constants.HUNDRED, transform.getMaxWeight());
        assertEquals(2, transform.getPackageDTOList().size());

        PackageDTO package1 = transform.getPackageDTOList().get(0);
        PackageDTO package2 = transform.getPackageDTOList().get(1);

        // Sorted by weight, hence 2nd Id comes first.
        assertEquals(2, package1.getId());
        assertEquals(45, package1.getPrice());
        assertEquals(10, package1.getWeight());
        assertEquals("€", package1.getCurrency());

        assertEquals(1, package2.getId());
        assertEquals(405, package2.getPrice());
        assertEquals(50, package2.getWeight());
        assertEquals("€", package2.getCurrency());
    }

    @Test
    public void testTransformer_throwsException_whenFormatIsWrong() {
        String string1 = "81 : (1,,€45)";
        String string2 = "81 : (,10,€45)";
        String string3 = "81 : (,,)";
        String string4 = " : (1,10,€45)";
        String string5 = " : (1,10,45)";
        String string6 = "(1,10,45)";
        String string7 = "1,10,45)";
        String string8 = "-1 (1,10,€45)";

        List<String> stringList = Arrays.asList(string1, string2, string3,
                string4, string5, string6, string7, string8);

        for (String s : stringList) {
            assertThrows(RuntimeException.class, () -> transformer.transform(s));
        }
    }
}
