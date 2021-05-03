package com.mobiquity.handler;

import com.mobiquity.constant.Constants;
import com.mobiquity.exception.APIException;
import com.mobiquity.exception.APIRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PackageServiceTest {
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        packageService = new PackageServiceImpl();
    }

    @Test
    public void packTest_throwException_whenFileContentIsWrong() {
        Stream<String> stringStream1 = Stream.of("81 : (1,,€45)");
        Stream<String> stringStream2 = Stream.of("81 : (,10,€45)");
        Stream<String> stringStream3 = Stream.of("81 : (,,)");
        Stream<String> stringStream4 = Stream.of(" : (1,10,€45)");
        Stream<String> stringStream5 = Stream.of(" : (1,10,45)");
        Stream<String> stringStream6 = Stream.of("(1,10,45)");
        Stream<String> stringStream7 = Stream.of("1,10,45)");
        Stream<String> stringStream8 = Stream.of("-1 (1,10,€45)");

        List<Stream<String>> streams = Arrays.asList(stringStream1, stringStream2, stringStream3,
                stringStream4, stringStream5, stringStream6, stringStream7, stringStream8);

        streams.forEach(stringStream -> {
            Exception exception = assertThrows(APIException.class, () -> packageService.pack(stringStream));

            String expectedMessage = "Wrong format";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        });
    }

    @Test
    public void packTest_whenMaxWeightAndMaxPriceIsCorrect() {
        Stream<String> stringStream1 = Stream.of("100 : (1,10,€45) (2,10,€45)");
        Stream<String> stringStream2 = Stream.of("1 : (1,10,€45) (2,10,€45)");
        Stream<String> stringStream3 = Stream.of("0 : (1,10,€45) (2,10,€45)");

        List<Stream<String>> streams = Arrays.asList(stringStream1, stringStream2, stringStream3);

        streams.forEach(stringStream -> assertDoesNotThrow(() -> packageService.pack(stringStream)));
    }

    @Test
    public void packTest_throwException_whenMaxWeightIsWrong() {
        Stream<String> stringStream1 = Stream.of("101 : (1,10,€45) (2,10,€45)");

        Exception exception = assertThrows(APIException.class, () -> packageService.pack(stringStream1));

        String expectedMessage = String.format("Max weight is more than %s", Constants.MAX_WEIGHT / Constants.HUNDRED);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void packTest_throwException_whenWeightLimitExceeded() {
        Stream<String> stringStream1 = Stream.of("50 : (1,101,€45) (2,10,€45)");

        Exception exception = assertThrows(APIException.class, () -> packageService.pack(stringStream1));

        String expectedMessage = String.format("Weight limit exceeded. Expected <= %s", Constants.MAX_WEIGHT / Constants.HUNDRED);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void packTest_throwException_whenPriceIsWrong() {
        Stream<String> stringStream1 = Stream.of("50 : (1,50,€405) (2,10,€45)");

        Exception exception = assertThrows(APIException.class, () -> packageService.pack(stringStream1));

        String expectedMessage = String.format("Value limit exceeded. Expected <= %s", Constants.MAX_PRICE);
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void packTest_throwException_whenCurrencyIsWrong() {
        Stream<String> stringStream1 = Stream.of("50 : (1,50,40) (2,10,€45)");
        Stream<String> stringStream2 = Stream.of("50 : (1,50,$40) (2,10,€45)");

        List<Stream<String>> streams = Arrays.asList(stringStream1, stringStream2);

        streams.forEach(stringStream -> {
            Exception exception = assertThrows(APIException.class, () -> packageService.pack(stringStream));

            String expectedMessage = "Please check provided currencies";
            String actualMessage = exception.getMessage();
            assertEquals(expectedMessage, actualMessage);
        });
    }
}