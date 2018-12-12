package com.github.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    private int total;
    private int min;
    private int max;

    @JsonCreator
    public Request(
            @JsonProperty("total") int total,
            @JsonProperty("min") int min,
            @JsonProperty("max") int max
    ) {
        this.total = total;
        this.min = min;
        this.max = max;
    }

    public int getTotal() {
        return total;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request that = (Request) o;

        if (total != that.total) return false;
        if (min != that.min) return false;
        return max == that.max;

    }

    @Override
    public int hashCode() {
        int result = total;
        result = 31 * result + min;
        result = 31 * result + max;
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "total=" + total +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
