package com.faskan.todo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Todo(@JsonProperty("_id") String id, String name, String description) {
}
