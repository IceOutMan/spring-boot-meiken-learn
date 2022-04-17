package com.meiken.format.impl;

import com.alibaba.fastjson.JSON;
import com.meiken.format.FormatHandler;

public class JsonFormatHandler implements FormatHandler {

    @Override
    public <T> String format(T t) {
        return "JsonFormatHandler:" + JSON.toJSONString(t);
    }
}
