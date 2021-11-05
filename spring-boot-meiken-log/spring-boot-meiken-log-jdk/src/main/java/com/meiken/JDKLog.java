package com.meiken;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

/**
 * Hello world!
 */
public class JDKLog {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(JDKLog.class.getName());

        // Level
        logger.setLevel(Level.INFO);

        //Handler
        FileHandler fileHandler = new FileHandler("/Users/gulinfei/temp/log/jdk.log");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                Date date = new Date();
                date.setTime(record.getMillis());
                return "\n日志记录时间:" + date.toString() + " # " + "日志等级:" + record.getLevel() + " # " + "日志信息:" + record.getMessage() + "#" + "全限定类名:" + record.getSourceClassName() + " # " + "方法名：" + record.getSourceMethodName();
            }
        });
        logger.addHandler(fileHandler);


        logger.info("Meiken Info");
        logger.warning("Meiken Waring");

    }
}
