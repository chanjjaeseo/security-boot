package com.elliot.security.core.validate.processor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> holder;

    public ValidateCodeProcessor findProcessorByType(String type) {
        Iterator<Map.Entry<String, ValidateCodeProcessor>> iterable = holder.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, ValidateCodeProcessor> entry = iterable.next();
            if (StringUtils.contains(entry.getKey(), type)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
