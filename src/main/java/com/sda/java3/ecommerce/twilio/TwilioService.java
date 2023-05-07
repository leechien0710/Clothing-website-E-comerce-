package com.sda.java3.ecommerce.twilio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Service
public class TwilioService {
	@Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    
    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;
    
    @Value("${twilio.phone.number}")
    private String TWILIO_NUMBER;
    
    public void sendSms(String toNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
            new PhoneNumber(toNumber),
            new PhoneNumber(TWILIO_NUMBER),
            "Thank you so much")
            .create();
}
}