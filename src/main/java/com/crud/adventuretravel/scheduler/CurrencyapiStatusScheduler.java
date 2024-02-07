package com.crud.adventuretravel.scheduler;

import com.crud.adventuretravel.config.AdminConfig;
import com.crud.adventuretravel.currencyapi.domain.status.CurrencyapiStatus;
import com.crud.adventuretravel.currencyapi.service.CurrencyapiStatusService;
import com.crud.adventuretravel.emailservice.EmailService;
import com.crud.adventuretravel.emailservice.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyapiStatusScheduler {

    private static final String SUBJECT = "Currencyapi status";
    private final CurrencyapiStatusService currencyapiStatusService;
    private final EmailService emailService;
    private final AdminConfig adminConfig;


    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(fixedRate = 10000000)
    public void saveCurrencyStatus() {

        CurrencyapiStatus currencyapiStatus = currencyapiStatusService.fetchStatus();
        if (currencyapiStatus.getRemaining() < 50) {
            emailService.send(
                    new Mail.MailBuilder()
                            .mailTo(adminConfig.getAdminMail())
                            .subject(SUBJECT)
                            .message(currencyapiStatus.getRemaining() + " queries remaining for Currencyapi")
                            .build());
        }
    }
}
