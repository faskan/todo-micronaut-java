package com.faskan.todo;

import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class TodoResourceIT {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void shouldSaveTodo() throws JSONException {
        HttpResponse httpResponse = client.toBlocking().exchange(request(), Todo.class);
        assertEquals(HttpStatus.OK, httpResponse.getStatus());

        String response = client.toBlocking()
                .retrieve(HttpRequest.GET("/api/todos"));
        JSONAssert.assertEquals("""
                [
                  {
                    "name": "test",
                    "description": "description"
                  }
                ]
                """, response, JSONCompareMode.LENIENT);
    }

    private HttpRequest<Todo> request() {
        return HttpRequest.POST("/api/todos", new Todo("", "test", "description"));
    }
}
