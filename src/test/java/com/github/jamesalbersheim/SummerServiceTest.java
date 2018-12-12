package com.github.jamesalbersheim;

import com.github.model.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@WebMvcTest(SummerController.class)
public class SummerServiceTest {

    private SummerService service;
    private List<String> expectedCombos = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        service = new SummerService();
        expectedCombos.add("1+2+3+4");
        expectedCombos.add("2+3+5");
        expectedCombos.add("1+4+5");
    }

    @Test
    public void testGetCombos() {
        List<String> result = service.getCombos(new Request(10, 1, 5));
        System.out.println("Combos:"+result);
        assertThat(result,equalTo(expectedCombos));
    }

    @Test
    public void testGetAllPermutations() {
        List<List<Integer>> allPermutations = service.getAllPermutations(asList(2, 3, 4, 5));
        System.out.println(allPermutations);
    }

}