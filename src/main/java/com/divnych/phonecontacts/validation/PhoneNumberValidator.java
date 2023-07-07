package com.divnych.phonecontacts.validation;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.lookups.v1.PhoneNumber;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configurable
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilioAccountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replaceAll("[\\s()-]", "");
        if ("".equals(value)) {
            return false;
        }
        try {
            PhoneNumber.fetcher(new com.twilio.type.PhoneNumber(value)).fetch();
            return true;
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

}
