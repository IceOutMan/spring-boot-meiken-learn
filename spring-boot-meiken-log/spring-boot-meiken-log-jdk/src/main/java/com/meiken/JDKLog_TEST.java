package com.meiken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class JDKLog_TEST {

    // 设置下存放日志的目录
    public static final String LOG_FOLDER = "/Users/xmly/temp/logs";

    // 配置在 $JDK_HOME$/jre/lib/logging.properties
    // 配置了 FileHandler -> {user.home}/java%u.log
    // 配置了 ConsoleHandler
    public static void main(String[] args) throws IOException {
        Logger myJdkLogger = initMyJdkLogger();
        // 不使用父类的 Handler 即，JDK提供了一个默认的 Handler
        myJdkLogger.setUseParentHandlers(false);
        myJdkLogger.warning("this is my jdk logger log");

        System.out.println("=======================分隔符=============================");

        Logger systemLogger = Logger.getLogger("SystemLogger");
//        systemLogger.setUseParentHandlers(false);
//        systemLogger.addHandler(new FileHandler());
        systemLogger.warning("this is system logger log");

    }


    private static Logger initMyJdkLogger() throws IOException {
        Logger logger = Logger.getLogger(JDKLog_TEST.class.getName());
        logger.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                Date date = new Date();
                date.setTime(record.getMillis());
                return "日志记录时间:" + date.toString() + " # " + "日志等级:" + record.getLevel() + " # " + "日志信息:" + record.getMessage() + "#" + "全限定类名:" + record.getSourceClassName() + " # " + "方法名：" + record.getSourceMethodName() + "\n";
            }
        });
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler(getCurrentDateFilePath());
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                Date date = new Date();
                date.setTime(record.getMillis());
                return "日志记录时间:" + date.toString() + " # " + "日志等级:" + record.getLevel() + " # " + "日志信息:" + record.getMessage() + "#" + "全限定类名:" + record.getSourceClassName() + " # " + "方法名：" + record.getSourceMethodName() + "\n";
            }
        });
        logger.addHandler(fileHandler);



        return logger;

    }



    public static String getCurrentDateFilePath(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateStr = simpleDateFormat.format(new Date());
        return  LOG_FOLDER + File.separator + "JDKLog_" + currentDateStr + ".log";
    }

}
