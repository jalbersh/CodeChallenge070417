package com.github.jamesalbersheim;

import com.github.model.Request;
import com.github.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@RestController
public class SummerController {
    private SummerService summerService;

    public SummerController(SummerService summerService) {
        this.summerService = summerService;
    }

    @RequestMapping(value = "/findSummer", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> findSummer(@RequestBody Request request) {
        List<String> strings = summerService.getCombos(request);
        if (strings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new Response(strings), HttpStatus.OK);
        }
    }

}
