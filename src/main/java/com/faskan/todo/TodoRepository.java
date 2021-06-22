package com.faskan.todo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Bean
public class TodoRepository {
    @Inject
    MongoClient mongoClient;

    public List<Todo> getAll() {
        MongoCollection<Todo> collection = JacksonMongoCollection.builder().build(mongoClient,
                "todos-app", "todos", Todo.class, UuidRepresentation.STANDARD);
        return StreamSupport.stream(collection.find().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void save(Todo todo) {
        MongoCollection<Todo> collection = JacksonMongoCollection.builder().build(mongoClient,
                "todos-app", "todos", Todo.class, UuidRepresentation.STANDARD);
        collection.insertOne(todo);
    }
}
