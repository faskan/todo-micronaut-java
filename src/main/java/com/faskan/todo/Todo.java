package com.faskan.todo;

import com.fasterxml.jackson.annotation.JsonProperty;

//public record Todo(@JsonProperty("_id") String id, String name, String description) {
//}

public class Todo {
    @JsonProperty("_id")
    private String _id;
    private String name;
    private String description;

    public Todo() {
    }

    public Todo(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
