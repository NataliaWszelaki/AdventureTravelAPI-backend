package com.crud.adventuretravel.emailservice;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public final class Mail {

    private final String mailTo;
    private List<String> toCcList = new ArrayList<>();
    private final String subject;
    private final String message;

    public static class MailBuilder {
        private String mailTo;
        private List<String> toCcList = new ArrayList<>();
        private String subject;
        private String message;

        public MailBuilder mailTo(String mailTo) {

            this.mailTo = mailTo;
            return this;
        }

        public MailBuilder toCc(String toCc) {

            toCcList.add(toCc);
            return this;
        }

        public MailBuilder subject(String subject) {

            this.subject = subject;
            return this;
        }

        public MailBuilder message(String message) {

            this.message = message;
            return this;
        }

        public Mail build() {

            return new Mail(mailTo, toCcList, subject, message);
        }
    }

    private Mail(final String mailTo, List<String> toCcList, final String subject, final String message) {
        this.mailTo = mailTo;
        this.toCcList = new ArrayList<>(toCcList);
        this.subject = subject;
        this.message = message;
    }
}