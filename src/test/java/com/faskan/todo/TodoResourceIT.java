package com.faskan.todo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@Testcontainers
@MicronautTest
public class TodoResourceIT {

    //@Container
    private static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:4.2"));
    private static EmbeddedServer embeddedServer;
    private static HttpClient client;


    static {
        mongoDBContainer.start();
        System.setProperty("mongodb.uri",
                mongoDBContainer.getReplicaSetUrl("micronaut"));
    }

    //    @BeforeAll - an alternative
//    public static void init() {
//        embeddedServer = ApplicationContext.run(EmbeddedServer.class, PropertySource.of(
//                "test", Map.of("mongodb.uri", mongoDBContainer.getReplicaSetUrl("micronaut"))
//        ));
//        client = embeddedServer.getApplicationContext().getBean(HttpClient.class);
//    }


    @Test
    void shouldSaveTodo() throws JSONException {
        HttpResponse httpResponse = client.toBlocking().exchange(request(), Todo.class);
        assertEquals(HttpStatus.OK, httpResponse.getStatus());

        String response = client.toBlocking()
                .retrieve(HttpRequest.GET(embeddedServer.getURL()+"/api/todos"));
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
        return HttpRequest.POST(embeddedServer.getURL()+"/api/todos", new Todo("test", "description"));
    }
}
