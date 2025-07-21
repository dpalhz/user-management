package com.service.authentication.util;

import com.service.authentication.dto.response.ApiResponse;

public class ApiResponseUtil {

  public static <T> ApiResponse<T> success(int code, String message, T data) {
    return ApiResponse.<T>builder().success(true).code(code).message(message).data(data).build();
  }

  public static <T> ApiResponse<T> success(T data) {
    return success(200, "Success", data);
  }

  public static <T> ApiResponse<T> fail(int code, String message) {
    return ApiResponse.<T>builder().success(false).code(code).message(message).data(null).build();
  }
}
