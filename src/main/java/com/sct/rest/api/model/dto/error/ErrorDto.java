package com.sct.rest.api.model.dto.error;

import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    private ErrorCodeEnum errorCode;

    private String message;
}
