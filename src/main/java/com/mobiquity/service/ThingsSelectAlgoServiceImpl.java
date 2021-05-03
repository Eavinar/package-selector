package com.mobiquity.service;

import com.mobiquity.dto.ThingsWrapper;
import com.mobiquity.helper.PackageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ThingsSelectAlgoService}.
 * Class is responsible for running algorithm in order to identify required things.
 */
public class ThingsSelectAlgoServiceImpl implements ThingsSelectAlgoService {
    private static final Logger log = LogManager.getLogger(ThingsSelectAlgoServiceImpl.class);

    /**
     * @inheritDoc
     */
    @Override
    public StringBuilder select(final Integer[] weight, final Integer[] value, final ThingsWrapper wrapper) {
        log.info("Starting things selecting algorithm");
        final int count = wrapper.getThingDTOList().size();
        final int maxWeight = wrapper.getMaxWeight();
        final int[][] matrix = new int[count + 1][maxWeight + 1];

        initializeMatrix(maxWeight, count, matrix);

        calculate(weight, value, maxWeight, matrix, count);

        StringBuilder itemsToInclude = findItemsToInclude(weight, maxWeight, count, matrix, wrapper);
        log.info("Finished things selecting algorithm. Things to be included to the post {}", itemsToInclude.toString());
        return itemsToInclude;
    }

    // Main look up process happens here. Method populates 2D matrix with values.
    // We are going to use those values to identify required things in order to be sent.
    private void calculate(final Integer[] weight, final Integer[] value, final int maxWeight, final int[][] matrix, final int count) {
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j <= maxWeight; j++) {

                matrix[i][j] = matrix[i - 1][j];

                if ((j >= weight[i - 1])) {
                    int newValue = matrix[i - 1][j - weight[i - 1]] + value[i - 1];
                    matrix[i][j] = Math.max(matrix[i][j], newValue);
                }
            }
        }
    }

    // Method is going through the matrix to find required things and then puts IDs into the string builder.
    private StringBuilder findItemsToInclude(final Integer[] weight, final int maxWeight, final int count, final int[][] matrix, final ThingsWrapper wrapper) {
        final List<Integer> indexes = getIndexes(weight, maxWeight, count, matrix);

        final StringBuilder builder = new StringBuilder();
        builder.append(PackageHelper.getString(wrapper.getThingDTOList(), indexes));

        return builder;
    }

    // Method grabs indexes of required things
    private List<Integer> getIndexes(final Integer[] weight, int maxWeight, int count, final int[][] b) {
        final List<Integer> indexes = new ArrayList<>();
        while (count != 0) {
            if (b[count][maxWeight] != b[count - 1][maxWeight]) {
                indexes.add(count);
                maxWeight = maxWeight - weight[count - 1];
            }
            count--;
        }
        return indexes;
    }

    // here we initialize our matrix with default (0) values.
    private void initializeMatrix(final int maxWeight, final int count, final int[][] b) {
        for (int i = 0; i <= count; i++) {
            for (int j = 0; j <= maxWeight; j++) {
                b[i][j] = 0;
            }
        }
    }
}
