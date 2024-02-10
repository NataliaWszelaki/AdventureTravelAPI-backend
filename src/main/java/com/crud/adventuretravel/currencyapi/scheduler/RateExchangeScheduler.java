package com.crud.adventuretravel.currencyapi.scheduler;

import com.crud.adventuretravel.config.AdminConfig;
import com.crud.adventuretravel.currencyapi.service.RateExchangeService;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.emailservice.Mail;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class RateExchangeScheduler {

    private static final String SUBJECT = "The data retrieval process has failed";
    private final RateExchangeService rateExchangeService;
    private final EmailService emailService;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(fixedRate = 10000000)
    public void saveCurrencyStatus() {
        log.info("Initiating data retrieval from Currencyapi...");
        try {
            rateExchangeService.fetchLatestExchangeRateEuroToPln();
            log.info("The data has been retrieved.");
        } catch (Exception e) {
            log.error("The data retrieval process has failed: " + e.getMessage(), e);
            emailService.send(
                    new Mail.MailBuilder()
                            .mailTo(adminConfig.getAdminMail())
                            .subject(SUBJECT)
                            .message("The data retrieval process has failed: " + e.getMessage())
                            .build());
        }
    }
}
