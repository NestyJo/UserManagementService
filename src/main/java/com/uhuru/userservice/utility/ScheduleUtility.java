package com.uhuru.userservice.utility;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleUtility {

    public ScheduleUtility(LoggerService logger) {
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void runScheduledService(){
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void processReceivedBatch(){
    }
}
