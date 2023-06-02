package com.gima.gimastore.util;

import com.gima.gimastore.constant.ResponseCodes;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.model.supplyProcess.SupplyProcessRequest;
import com.gima.gimastore.model.UserDTO;
import com.google.gson.Gson;
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
    public static UserDTO formattedJsonToUserDTOObject(String stringDTO) {
        Gson gson = new Gson();
        return gson.fromJson(stringDTO, UserDTO.class);

    }
    public static PartDTO formattedJsonToPartDTOObject(String stringDTO) {
        Gson gson = new Gson();
        return gson.fromJson(stringDTO, PartDTO.class);
    }
    public static SupplyProcessRequest formattedJsonToSupplyProcessRequestObject(String stringDTO) {
        Gson gson = new Gson();
        return gson.fromJson(stringDTO, SupplyProcessRequest.class);
    }
}
