package com.mobiquity.dto;

import java.util.List;

/**
 * Wrapper class for the list of {@link PackageDTO}
 */
public class PackageWrapper {

    private final int maxWeight;
    private final List<PackageDTO> packageDTOList;

    public PackageWrapper(int maxWeight, List<PackageDTO> packageDTOList) {
        this.maxWeight = maxWeight;
        this.packageDTOList = packageDTOList;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<PackageDTO> getPackageDTOList() {
        return packageDTOList;
    }
}
