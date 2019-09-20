package com.kakaopay.homework.internetbanking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogisticRegression {

    public static Double predictForValue(List<Integer> x, List<Double> y , int predictForDependentVariable) {
        if (x.size() != y.size())
            throw new IllegalStateException("Must have equal X and Y data points");

        Integer numberOfDataValues = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Double> xMultipliedByY = new ArrayList<>();
        for (int i = 0; i < numberOfDataValues; i++) {
            Double i1 = (Double) (x.get(i) * y.get(i));
            //Integer integer = i1;
            xMultipliedByY.add(i1);
        }

        Integer xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return (slope * predictForDependentVariable) + intercept;
    }

}
