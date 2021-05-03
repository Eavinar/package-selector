package com.mobiquity.transformer;

import com.mobiquity.constant.Constants;
import com.mobiquity.dto.PackageDTO;
import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIRuntimeException;
import com.mobiquity.factory.PackageSortFactory;
import com.mobiquity.handler.PackageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class is responsible for transforming String into {@link PackageDTO} class.
 */
public class StringToPackageDTOTransformer {
    private static final Logger log = LogManager.getLogger(StringToPackageDTOTransformer.class);

    /**
     * @param input is what should be transformed
     * @return Wrapper class for the list of {@link PackageDTO}
     */
    public PackageWrapper transform(final String input) {
        log.info("Starting for transformation of the String into DTO");
        final String[] split = input.split("[(|)]");
        try {
            final int maxWeight = Integer.parseInt(split[0].substring(0, split[0].indexOf(":")).trim()) * Constants.HUNDRED;
            final List<PackageDTO> packageDTOList = Arrays.stream(split).skip(1)
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(String::isBlank))
                    .map(s -> new PackageDTO.PackageDTOBuilder().build(s.split(",")))
                    // sort by weight as we want to return the package with less weight when price is equal
                    .sorted(PackageSortFactory.factory(Constants.SORT_BY))
                    .collect(Collectors.toList());

            log.info("Finished transformation.");
            return new PackageWrapper(maxWeight, packageDTOList);

        } catch (RuntimeException e) {
            log.error("Possible wrong format occurred.", e);
            throw new APIRuntimeException("Wrong format.", e);
        }
    }
}
