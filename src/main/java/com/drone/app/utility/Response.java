package com.drone.app.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Response<T> {

    private T data;
    private URI uri;

    public Response (T data, URI uri) {
        this.data = data;
        this.uri = uri;
    }
}
