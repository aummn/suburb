package com.aummn.suburb.resource.dto.response;

import com.aummn.suburb.exception.ErrorResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseWebResponse<T> {
    private ErrorResponse error;
    private T data;

    public static BaseWebResponse successNoData() {
        return BaseWebResponse.builder()
                .build();
    }

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .build();
    }

    public static BaseWebResponse error(ErrorResponse error) {
        return BaseWebResponse.builder()
                .error(error)
                .build();
    }
}
