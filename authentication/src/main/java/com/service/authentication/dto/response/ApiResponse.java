package com.service.authentication.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

  @Schema(example = "true")
  private boolean success;

  @Schema(example = "200")
  private int code;

  @Schema(example = "Request successfully processed")
  private String message;

  private T data;
}
