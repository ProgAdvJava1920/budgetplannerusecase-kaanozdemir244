package be.pxl.student.rest;

import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resources.PaymenCreateResource;
import be.pxl.student.rest.resources.PaymentResource;
import be.pxl.student.service.AccountService;

import javax.inject.Inject;
import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRest {
    @Inject
    private AccountService accountService;

    @GET
    @Path("{name}")
    @Produces("application/json")
    public Response getPayments(@PathParam("name") String name) {
        try {
            List<Payment> payments = accountService.findPaymentsByAccountName(name);
            return Response.ok(mapPayments(payments)).build();
        } catch (AccountNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymenCreateResource paymenCreateResource) {
        try {
            accountService.addPayments(name, paymenCreateResource.getCounterAccount(), paymenCreateResource.getAmount(), paymenCreateResource.getDetail());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch (AccountNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(ex.getMessage()).build();
        }
    }


    private List<PaymentResource> mapPayments(List<Payment> payments) {
        return payments.stream()
                .map(this::mapPayment)
                .collect(Collectors.toList());
    }

    private PaymentResource mapPayment(Payment payment) {
        PaymentResource result = new PaymentResource();
        result.setId(payment.getId());
        result.setAmount(payment.getAmount());
        result.setCounterAccount(payment.getCounterAccount().getIBAN());
        result.setCurrency(payment.getCurrency());
        result.setDetail(payment.getDetail());
        return result;
    }
}
