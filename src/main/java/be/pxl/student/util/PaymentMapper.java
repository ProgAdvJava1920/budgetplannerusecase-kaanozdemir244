package be.pxl.student.util;

import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PaymentMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            "EEE MMM dd HH:mm:ss zzz yyyy",
            Locale.ENGLISH
    );

    public Payment map(String validLine) throws InvalidPaymentException{
        String[] splitLines = validLine.split(",");
        if(splitLines.length!=7){
            throw  new InvalidPaymentException("Invalid number of fields in line.");
        }
        return new Payment(
                LocalDateTime.parse(splitLines[3], FORMATTER),
                Float.parseFloat(splitLines[4]),
                splitLines[5],
                splitLines[6]
        );
    }
}
