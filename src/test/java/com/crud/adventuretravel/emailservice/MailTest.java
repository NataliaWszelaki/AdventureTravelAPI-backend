package com.crud.adventuretravel.emailservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MailTest {

    @Test
    void shouldCreateNewMail() {

        //Given
        Mail mail = new Mail.MailBuilder()
                .mailTo("test@test.pl")
                .toCc("test1@test.pl")
                .toCc("test2@test.pl")
                .subject("Subject test")
                .message("Message test")
                .build();

        //When
        int listSize = mail.getToCcList().size();

        //Then
        assertEquals(2, listSize);
    }
}