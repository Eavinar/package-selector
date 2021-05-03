package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackerTest {

    @Test
    public void packTest_whenFileExists() throws APIException, IOException {
        Path expectedResultFilePath = Paths.get(".").resolve("src/main/test/resources/example_output").toAbsolutePath();
        String expected = Files.readString(expectedResultFilePath);

        String filePath = Paths.get(".").resolve("src/main/test/resources/example_input").toAbsolutePath().toString();
        assertEquals(expected, Packer.pack(filePath), "Actual result is different from expected");
    }

    @Test
    public void packTest_throwException_whenFileNotExists() {
        String filePath = "wrong_path";
        Exception exception = assertThrows(APIException.class, () -> Packer.pack(filePath));

        String expectedMessage = "File not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void packTest_whenFileIsEmpty() throws APIException {
        String filePath = Paths.get(".").resolve("src/main/test/resources/empty_input").toAbsolutePath().toString();
        String expectedMessage = "";
        String actualMessage = Packer.pack(filePath);
        assertEquals(expectedMessage, actualMessage);
    }
}