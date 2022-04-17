package com.meiken.format;

/**
 * 模仿 Spring 的 Template
 * @Author glf
 * @Date 2022/4/17
 */
public class MeikenFormatTemplate {

    private FormatHandler formatHandler;
    private MeikenFormatProperties meikenFormatProperties;

    public MeikenFormatTemplate(FormatHandler formatHandler, MeikenFormatProperties meikenFormatProperties){
        this.formatHandler = formatHandler;
        this.meikenFormatProperties = meikenFormatProperties;
    }

    // 定义一个格式化方法，具体的实现由 FormatHandler实现类实现
    public <T> String doFormat(T t){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Begin: Execute format").append("<br/>");
        stringBuilder.append("MeikenFormatProperties:").append(meikenFormatProperties.getAppOwner()).append("<br/>")
                .append(formatHandler.format(meikenFormatProperties.getInfos())).append("<br/>");
        stringBuilder.append("Ojb format result:").append(formatHandler.format(t)).append("<br/>");
        return stringBuilder.toString();
    }
}

