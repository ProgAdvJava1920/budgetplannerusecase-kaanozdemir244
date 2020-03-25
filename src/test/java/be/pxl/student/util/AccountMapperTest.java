package be.pxl.student.util;

import be.pxl.student.entity.Account;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountMapperTest {
    private String validLine = "Jos,BE69771770897312,BE17795215960626,Thu Feb 13 05:47:35 CET 2020,265.8,EUR,Ut ut necessitatibus itaque ullam.";
    @Test
    public void aValidLineIsMappedToAnAccount() throws InvalidPaymentException {
        Account result = AccountMapper.mapToAccount(validLine);
        assertNotNull(result);
        assertEquals("Jos",result.getName());
        assertEquals("BE69771770897312",result.getIBAN());
        assertEquals(1,result.getPayments().size());
        assertEquals(LocalDateTime.of(2020,2,13,5,47,35),result.getPayments().get(0).getDate());
        assertEquals("EUR",result.getPayments().get(0).getCurrency());
        assertEquals(265.8,result.getPayments().get(0).getAmount(), 0.01);
    }
}
