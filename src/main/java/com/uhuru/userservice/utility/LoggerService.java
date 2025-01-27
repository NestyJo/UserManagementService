package com.uhuru.userservice.utility;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LoggerService {

    private final Logger logger = Logger.getLogger(LoggerService.class.getName());

    public void log(Object object){
        log("--->>> " + object  + " <<<---", Level.INFO.toString());
    }

    public void logP(Object object){
        log(object, Level.INFO.toString());
    }

    public void log(Object object, String type){
        if(object == null) return;
        String data = object.toString();
        Level level = Level.toLevel(type);

        if(type == null || Level.toLevel(type) == null){
            logger.info(data); return;
        }

        type = type.toUpperCase(Locale.ROOT);
        Level logLevel = Level.toLevel(type);
        if(logLevel.equals(Level.INFO)){
            logger.info(data);
        }else if(logLevel.equals(Level.DEBUG)){
            logger.debug(data);
        }else if(logLevel.equals(Level.WARN)){
            logger.warn(data);
        }else if(logLevel.equals(Level.ERROR)){
            logger.error(data);
        }else if(logLevel.equals(Level.TRACE)){
            logger.trace(data);
        }else if(logLevel.equals(Level.FATAL)){
            logger.fatal(data);
        }else{
            logger.info(data);
        }
    }
}
