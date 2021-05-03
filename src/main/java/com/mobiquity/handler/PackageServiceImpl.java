package com.mobiquity.handler;

import com.mobiquity.constant.ValidatationStatus;
import com.mobiquity.dto.PackageDTO;
import com.mobiquity.dto.PackageWrapper;
import com.mobiquity.exception.APIException;
import com.mobiquity.exception.APIRuntimeException;
import com.mobiquity.helper.PackageHelper;
import com.mobiquity.helper.Utility;
import com.mobiquity.packer.Packer;
import com.mobiquity.service.PackageSelectAlgoService;
import com.mobiquity.service.PackageSelectAlgoServiceImpl;
import com.mobiquity.transformer.StringToPackageDTOTransformer;
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

    private StringToPackageDTOTransformer transformer;
    private PackageSelectAlgoService packageSelectService;
    private List<PackageValidator> validatorList;

    public void setTransformer(final StringToPackageDTOTransformer transformer) {
        this.transformer = transformer;
    }

    public void setPackageSelectService(final PackageSelectAlgoService packageSelectService) {
        this.packageSelectService = packageSelectService;
    }

    public void setValidatorList(final List<PackageValidator> validatorList) {
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
            final List<PackageWrapper> packageWrappers = transformAndGetWrapperList(stringStream);

            for (final PackageWrapper wrapper : packageWrappers) {
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
            setTransformer(new StringToPackageDTOTransformer());
        }

        if (packageSelectService == null) {
            setPackageSelectService(new PackageSelectAlgoServiceImpl());
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

    private List<PackageWrapper> transformAndGetWrapperList(final Stream<String> stringStream) {
        return stringStream
                .map(transformer::transform)
                .collect(Collectors.toList());
    }

    private void choosePacks(final StringBuilder builder, final PackageWrapper wrapper) throws APIException {
        List<PackageDTO> packageDTOList = wrapper.getPackageDTOList();

        Integer[] weight = PackageHelper.getWeights(packageDTOList);
        Integer[] value = PackageHelper.getValues(packageDTOList);

        validate(weight, value, wrapper);

        choose(builder, wrapper, weight, value);
    }

    private void validate(final Integer[] weight, final Integer[] value, final PackageWrapper wrapper) throws APIException {
        for (final PackageValidator validator : validatorList) {
            log.info("Running {}", validator.name());
            validator.validate(weight, value, wrapper);
            log.info("Running {}. Status: {}", validator.name(), ValidatationStatus.SUCCESS);
        }
    }

    private void choose(final StringBuilder builder, final PackageWrapper wrapper, final Integer[] weight, final Integer[] value) {
        StringBuilder selectedIndexes = packageSelectService.select(weight, value, wrapper);

        builder.append(selectedIndexes);
        builder.append("\n");
    }
}
