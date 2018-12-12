package com.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Response {
    private List<String> solutions;

    public Response(@JsonProperty("solutions") List<String> solutions) {
        this.solutions = solutions;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response that = (Response) o;

        return !(solutions != null ? !solutions.equals(that.solutions) : that.solutions != null);

    }

    @Override
    public int hashCode() {
        return solutions != null ? solutions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Response{" +
                "solutions=" + solutions +
                '}';
    }
}
