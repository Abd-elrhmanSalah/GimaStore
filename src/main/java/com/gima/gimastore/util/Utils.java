package com.gima.gimastore.util;

import com.gima.gimastore.constant.ResponseCodes;
import com.gima.gimastore.exception.StatusResponse;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public static boolean isNotEmpty(Object obj) {
        return obj != null;
    }

    public static StatusResponse internalServerError(String message) {

        StatusResponse error = new StatusResponse(ResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                ResponseCodes.INTERNAL_SERVER_ERROR.getKey(), message);
        return buildErrorMessage(error);
    }

    public static StatusResponse buildErrorMessage(StatusResponse response) {

        response.setMessage(response.getMessage());
        return response;
    }

}
