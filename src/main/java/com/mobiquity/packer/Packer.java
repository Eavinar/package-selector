package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.handler.PackageService;
import com.mobiquity.handler.PackageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Class is starting point for API calls.
 * Responsible for processing input file and returning the result.
 */
public class Packer {
    private static final Logger log = LogManager.getLogger(Packer.class);

    private final static PackageService packageService;

    static {
        packageService = new PackageServiceImpl();
    }

    private Packer() {
    }

    /**
     * API method starts evaluation and processing the file.
     * May return one or more results based on input file.
     * Output results line are corresponding to the input lines with details.
     *
     * @param filePath - absolute path to file.
     * @return String result of evaluation.
     * @throws APIException if file does not exist.
     */
    public static String pack(String filePath) throws APIException {
        log.info("Received path to file. Starting evaluation.");
        try (Stream<String> stringStream = Files.lines(Path.of(filePath))) {
            return packageService.pack(stringStream);
        } catch (IOException e) {
            log.error("File not found", e);
            throw new APIException("File not found", e);
        } catch (Exception e) {
            log.error("Unexpected Exception occurred", e);
            throw new APIException("Unexpected Exception occurred", e);
        }
    }
}
