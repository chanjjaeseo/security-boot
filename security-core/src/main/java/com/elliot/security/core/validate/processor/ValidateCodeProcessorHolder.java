package com.elliot.security.core.validate.processor;

import com.elliot.security.core.constant.ValidateCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {

    private static final String VALIDATE_CODE_PROCESSOR_SUFFIX = "ValidateCodeProcessor";

    @Autowired
    private Map<String, ValidateCodeProcessor> holder;

    public ValidateCodeProcessor getProcessorByType(ValidateCodeEnum validateCodeEnum) {
        String type = validateCodeEnum.getType();
        String processorName = type + VALIDATE_CODE_PROCESSOR_SUFFIX;
        return holder.get(processorName);
    }

}
