package com.elliot.security.core.validate;

import com.elliot.security.core.constant.ValidateCode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {

    private static final String VALIDATE_CODE_PROCESSOR_SUFFIX = "ValidateCodeProcessor";

    private Map<String, ValidateCodeProcessor> holder;

    public ValidateCodeProcessor getProcessorByType(ValidateCode validateCode) {
        String type = validateCode.getUrlSuffix();
        String processorName = type + VALIDATE_CODE_PROCESSOR_SUFFIX;
        return holder.get(processorName);
    }

}
