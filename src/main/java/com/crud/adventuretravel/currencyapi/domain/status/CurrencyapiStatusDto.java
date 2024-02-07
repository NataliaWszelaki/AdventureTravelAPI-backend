package com.crud.adventuretravel.currencyapi.domain.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyapiStatusDto {

    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("quotas")
    private QuotasDto quotas;
}
