package com.mobiquity.transformer;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringToThingDTOTransformerTest {

    private StringToThingsWrapperTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new StringToThingsWrapperTransformer();
    }

    @Test
    public void testTransformer() {
        String input = "50 : (1,50,€405) (2,10,€45)";
        ThingsWrapper transform = transformer.transform(input);

        assertEquals(50 * Constants.HUNDRED, transform.getMaxWeight());
        assertEquals(2, transform.getThingDTOList().size());

        ThingDTO thing1 = transform.getThingDTOList().get(0);
        ThingDTO thing2 = transform.getThingDTOList().get(1);

        // Sorted by weight, hence 2nd Id comes first.
        assertEquals(2, thing1.getId());
        assertEquals(45, thing1.getPrice());
        assertEquals(10, thing1.getWeight());
        assertEquals("€", thing1.getCurrency());

        assertEquals(1, thing2.getId());
        assertEquals(405, thing2.getPrice());
        assertEquals(50, thing2.getWeight());
        assertEquals("€", thing2.getCurrency());
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
