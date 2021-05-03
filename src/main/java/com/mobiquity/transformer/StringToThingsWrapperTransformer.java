package com.mobiquity.transformer;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIRuntimeException;
import com.mobiquity.factory.ThingSortFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class is responsible for transforming String into {@link ThingDTO} class.
 */
public class StringToThingsWrapperTransformer {
    private static final Logger log = LogManager.getLogger(StringToThingsWrapperTransformer.class);

    /**
     * @param input is what should be transformed
     * @return Wrapper class for the list of {@link ThingDTO}
     */
    public ThingsWrapper transform(final String input) {
        log.info("Starting for transformation of the String into DTO");
        final String[] split = input.split("[(|)]");
        try {
            final int maxWeight = Integer.parseInt(split[0].substring(0, split[0].indexOf(":")).trim()) * Constants.HUNDRED;
            final List<ThingDTO> thingDTOList = Arrays.stream(split).skip(1)
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(String::isBlank))
                    .map(s -> new ThingDTO.ThingDTOBuilder().build(s.split(",")))
                    // sort by weight as we want to return the things with less weight when price is equal
                    .sorted(ThingSortFactory.factory(Constants.SORT_BY))
                    .collect(Collectors.toList());

            log.info("Finished transformation.");
            return new ThingsWrapper(maxWeight, thingDTOList);

        } catch (RuntimeException e) {
            log.error("Possible wrong format occurred.", e);
            throw new APIRuntimeException("Wrong format.", e);
        }
    }
}
