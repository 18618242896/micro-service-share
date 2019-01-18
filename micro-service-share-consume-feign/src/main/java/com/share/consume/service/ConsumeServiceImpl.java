package com.share.consume.service;

import org.springframework.stereotype.Component;


/**
 * Fallback class for the specified Feign client interface. The fallback class must
 * implement the interface annotated by this annotation and be a valid spring bean.
 */
@Component
public class ConsumeServiceImpl implements ConsumeService {
    @Override
    public String get(Integer userId) {
        return "ConsumeServiceImpl fail back......";
    }
}
