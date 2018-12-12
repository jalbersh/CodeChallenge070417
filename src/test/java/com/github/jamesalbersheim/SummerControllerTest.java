package com.github.jamesalbersheim;

import com.github.model.Request;
import com.github.model.Response;
import com.google.gson.*;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@WebMvcTest(SummerController.class)
public class SummerControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private MockMvc mvc;

    private SummerService service;

    private SummerController controller;
    private String expectedResponseBody = "[\"1+2+3+4\", \"2+3+5\", \"1+4+5\"]";
    private List<String> expectedCombos = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        service = new SummerService();
        controller = new SummerController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        expectedCombos.add("1+2+3+4");
        expectedCombos.add("2+3+5");
        expectedCombos.add("1+4+5");
        restTemplate = new RestTemplate();
    }

    @Test
    public void testFindSummer() {
        ResponseEntity<Response> summer = controller.findSummer(new Request(10, 1, 5));
        System.out.println("foo:"+summer.getStatusCode());
        System.out.println("foo:"+summer.getBody());
    }

    @Test
    public void testCreateWithknownResult() {
        // given
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        server.expect(requestTo(
                String.format("http://localhost:8080/findSummer")))
                .andRespond(withSuccess("{\"total\":10,\"min\":1,\"max\":5}", MediaType.APPLICATION_JSON))
        ;

        // when
        JsonObject jsonObject = new JsonObject();
        JsonObject response = this.put("http://localhost:8080/findSummer", jsonObject).getAsJsonObject();

        // then
        JsonObject result = response.getAsJsonObject("solutions");

        assertThat(result, CoreMatchers.equalTo(expectedCombos));

        server.verify();
    }

    protected JsonElement put(String url, JsonObject payload) {
        return requestWithBody(url, payload, HttpMethod.PUT);
    }

    protected JsonElement requestWithBody(String url, JsonObject payload, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson builder = new GsonBuilder().create();
        String jsonString = builder.toJson(payload);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode(), CoreMatchers.equalTo(HttpStatus.OK));
        return new JsonParser().parse(responseEntity.getBody());
    }
}