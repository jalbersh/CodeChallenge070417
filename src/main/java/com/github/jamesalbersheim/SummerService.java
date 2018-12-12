package com.github.jamesalbersheim;

import com.github.model.Request;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Service
public class SummerService {

    public List<String> getCombos(Request request) {
        List<Integer> legalValues = getLegalValues(request);

        List<List<Integer>> allPermutations = getAllPermutations(legalValues);
        return allPermutations.stream()
                .filter(x -> addsUp(x, request.getTotal()))
                .sorted((y, z) -> Integer.compare(z.size(), y.size()))
                .map(w -> stringify(w))
                .distinct()
                .collect(toList());
    }

    public List<List<Integer>> getAllPermutations(List<Integer> values) {
        if (values.isEmpty()) return emptyList();

        List<List<Integer>> accumulator = new ArrayList<>();
        accumulator.add(values);
        for (int i = 0; i < values.size(); i++) {
            List<Integer> deep = deepCopy(values);
            deep.remove(i);
            List<List<Integer>> foo = getAllPermutations(deep);
            accumulator.addAll(foo);
        }

        return accumulator;
    }

    public List<Integer> deepCopy(List<Integer> input) {
        List<Integer> output = new ArrayList<>();
        output.addAll(input);
        return output;
    }

    public List<Integer> getLegalValues(Request request) {
        List<Integer> finalList = new ArrayList<>();
        for (int i = request.getMin(); i <= request.getMax(); i++) {
            finalList.add(i);
        }
        return finalList;
    }

    public boolean addsUp(List<Integer> input, Integer expectedSum) {
        if (input.stream().reduce(0, (a, b) -> a + b) == expectedSum) {
            return true;
        }
        return false;
    }

    public String stringify(List<Integer> input) {
        return input.stream().map(x -> Integer.toString(x)).collect(joining("+"));
    }
}
