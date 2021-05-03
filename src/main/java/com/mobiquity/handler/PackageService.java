package com.mobiquity.handler;

import com.mobiquity.exception.APIException;

import java.util.stream.Stream;

/**
 * Interface is responsible for getting the required packages id.
 */
public interface PackageService {

    /**
     * Service Layer method starts selecting appropriate packages from the input.
     * May return one or more results based on input file.
     * Output results line are corresponding to the input lines with details.
     *
     * @param stringStream is stream of Strings for further evaluation.
     *                     At the moment file content are populated into this stream.
     * @return String result of evaluation.
     * @throws APIException if file does not exist.
     */
    String pack(final Stream<String> stringStream) throws APIException;
}
