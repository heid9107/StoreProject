package com.techelevator.service;

import com.techelevator.model.StateTaxDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class SalesTaxService {
    RestTemplate restTemplate = new RestTemplate();
    public BigDecimal getTaxRate(String State) {
        StateTaxDto response = restTemplate.getForObject("https://teapi.netlify.app/api/statetax?state=" + State,
                StateTaxDto.class);
        return response.getSalesTax();
    }
}
