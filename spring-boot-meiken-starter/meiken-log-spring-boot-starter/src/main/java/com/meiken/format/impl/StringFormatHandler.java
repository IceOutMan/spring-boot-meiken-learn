package com.meiken.format.impl;

import com.meiken.format.FormatHandler;

public class StringFormatHandler implements FormatHandler {
    @Override
    public <T> String format(T t) {
        return "StringFormatHandler:"+t.toString();
    }
}
