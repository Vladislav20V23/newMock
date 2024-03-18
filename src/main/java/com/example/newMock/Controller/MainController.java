package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency = null;
            BigDecimal bal = null;
            Random random = new Random();

            String rqUID = requestDTO.getRqUID();

//            String otvet = "{\n" +        //можно просто как заглушкой без логики, в ретерн сразу добавить otvet
//                    "\t\"rqUID\": \"79dgtf565j8158f64gt4\",\n" +
//                    "\t\"clientId\": \"8050000000000000000\",\n" +
//                    "\t\"account\": \"80500000000000000001\",\n" +
//                    "\t\"currency\": \"US\",\n" +
//                    "\t\"balance\": \"1400.00\",\n" +
//                    "\t\"maxLimit\": \"2000.00\"\n" +
//                    "\t}";


            if(firstDigit == '8'){
                maxLimit = new BigDecimal(2000);
                double doubleMaxLimit = maxLimit.doubleValue();
                double balance = random.nextDouble(doubleMaxLimit);
                double floorNumber = Math.floor(balance*100.0)/100.0;
                BigDecimal bigDecimalMaxForBalance = BigDecimal.valueOf(floorNumber);
                bal = new BigDecimal(String.valueOf(bigDecimalMaxForBalance));     ////////////////
                currency = "US";
            }else if(firstDigit =='9'){
                maxLimit = new BigDecimal(1000);
                double doubleMaxLimit = maxLimit.doubleValue();
                double balance = random.nextDouble(doubleMaxLimit);
                double floorNumber = Math.floor(balance*100.0)/100.0;
                BigDecimal bigDecimalMaxForBalance = BigDecimal.valueOf(floorNumber);
                bal = new BigDecimal(String.valueOf(bigDecimalMaxForBalance));
                currency = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                double doubleMaxLimit = maxLimit.doubleValue();
                double balance = random.nextDouble(doubleMaxLimit);
                double floorNumber = Math.floor(balance*100.0)/100.0;
                BigDecimal bigDecimalMaxForBalance = BigDecimal.valueOf(floorNumber);
                bal = new BigDecimal(String.valueOf(bigDecimalMaxForBalance));
                currency = "RUB";
            }

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
           // responseDTO.setBalance(new BigDecimal(777));
            responseDTO.setBalance(bal);
            responseDTO.setMaxLimit(maxLimit);

            log.error("****** RequestDTO ******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("****** ResponseDTO ******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;


        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
