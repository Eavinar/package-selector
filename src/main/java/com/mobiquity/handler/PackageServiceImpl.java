package com.mobiquity.handler;

import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.ThingDTO;
import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.exception.APIException;
import com.mobiquity.exception.APIRuntimeException;
import com.mobiquity.helper.PackageHelper;
import com.mobiquity.helper.Utility;
import com.mobiquity.service.ThingsSelectAlgoService;
import com.mobiquity.service.ThingsSelectAlgoServiceImpl;
import com.mobiquity.transformer.StringToThingsWrapperTransformer;
import com.mobiquity.validator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link PackageService}
 * Responsible for processing input file and returning the result.
 */
public final class PackageServiceImpl implements PackageService {
    private static final Logger log = LogManager.getLogger(PackageServiceImpl.class);

    private StringToThingsWrapperTransformer transformer;
    private ThingsSelectAlgoService thingSelectService;
    private List<ThingValidator> validatorList;

    public void setTransformer(final StringToThingsWrapperTransformer transformer) {
        this.transformer = transformer;
    }

    public void setThingSelectService(final ThingsSelectAlgoService thingSelectService) {
        this.thingSelectService = thingSelectService;
    }

    public void setValidatorList(final List<ThingValidator> validatorList) {
        this.validatorList = validatorList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String pack(final Stream<String> stringStream) throws APIException {
        // initialize Default transformer & validators if they were not set, yet.
        initialize();

        final StringBuilder builder = new StringBuilder();

        try {
            final List<ThingsWrapper> thingsWrappers = transformAndGetWrapperList(stringStream);

            for (final ThingsWrapper wrapper : thingsWrappers) {
                choosePacks(builder, wrapper);
            }
        } catch (APIRuntimeException e) {
            log.error("Possible wrong format in the source.", e);
            throw new APIException("Wrong format", e);
        }
        return Utility.removeTrailingSpaces(builder.toString());
    }

    private void initialize() {
        if (transformer == null) {
            setTransformer(new StringToThingsWrapperTransformer());
        }

        if (thingSelectService == null) {
            setThingSelectService(new ThingsSelectAlgoServiceImpl());
        }

        if (validatorList == null || validatorList.isEmpty()) {
            setValidatorList(List.of(
                    new SizeValidator(),
                    new WeightValidator(),
                    new ValueValidator(),
                    new CurrencyValidator(),
                    new MaxWeightValidator(),
                    new SameIdValidator()
            ));
        }
    }

    private List<ThingsWrapper> transformAndGetWrapperList(final Stream<String> stringStream) {
        return stringStream
                .map(transformer::transform)
                .collect(Collectors.toList());
    }

    private void choosePacks(final StringBuilder builder, final ThingsWrapper wrapper) throws APIException {
        List<ThingDTO> thingDTOList = wrapper.getThingDTOList();

        Integer[] weight = PackageHelper.getWeights(thingDTOList);
        Integer[] value = PackageHelper.getValues(thingDTOList);

        validate(weight, value, wrapper);

        choose(builder, wrapper, weight, value);
    }

    private void validate(final Integer[] weight, final Integer[] value, final ThingsWrapper wrapper) throws APIException {
        for (final ThingValidator validator : validatorList) {
            log.info("Running {}", validator.name());
            validator.validate(weight, value, wrapper);
            log.info("Running {}. Status: {}", validator.name(), ValidatationStatus.SUCCESS);
        }
    }

    private void choose(final StringBuilder builder, final ThingsWrapper wrapper, final Integer[] weight, final Integer[] value) {
        StringBuilder selectedIndexes = thingSelectService.select(weight, value, wrapper);

        builder.append(selectedIndexes);
        builder.append("\n");
    }
}
