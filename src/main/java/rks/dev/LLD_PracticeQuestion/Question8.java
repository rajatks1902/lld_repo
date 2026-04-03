package rks.dev.LLD_PracticeQuestion;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract class Validation {

    public Validation validateRequest;

    abstract void ValidateAndExecute(PaymentRequest request) throws IOException;

    public void nextValidation(Validation currentValidation) {
        this.validateRequest = currentValidation;
    }
}

class AuthenticationCheck extends Validation {

    @Override
    void ValidateAndExecute(PaymentRequest request) throws IOException {
        System.out.println("AuthenticationCheck");
        if (request.name() == null || request.name().isEmpty()) {
            System.out.println("AuthenticationCheck Failed");
            throw new IOException("Invalid User");

        }
        if (validateRequest != null) {
            validateRequest.ValidateAndExecute(request);
        }
    }
}

class Fraud extends Validation {

    @Override
    void ValidateAndExecute(PaymentRequest request) throws IOException {
        System.out.println("FraudCheck");
        if (request.amt() > 100000) {
            System.out.println("Potential Fraud Detected ");
            throw new IOException("Potential Fraud");
        }
        if (validateRequest != null) {
            validateRequest.ValidateAndExecute(request);
        }
    }
}

class BalanceCheck extends Validation {

    @Override
    void ValidateAndExecute(PaymentRequest request) throws IOException {
        if (request.amt() < 0) {
            System.out.println("Invalid Request : Payment Declined");
            throw new IOException("Invalid Request");
        }
        System.out.println("BalanceCheck");
        if (validateRequest != null) {
            validateRequest.ValidateAndExecute(request);
        }

    }
}

record PaymentRequest(String name, int amt, PAYMENT_MODE mode) {

}

enum PAYMENT_MODE {
    UPI, CREDIT_CARD, PAYPAL
}

interface Payment {
    void makePayment(int x);
}

class Paypal {

    void makePaymentInDollar(int y) {
        System.out.println("Amount Payed through PayPal in $ :" + y);

    }
}

class UPI implements Payment {

    @Override
    public void makePayment(int x) {
        System.out.println("Payment Done through UPI in Rs :" + x);
    }
}

class CreditCard implements Payment {

    @Override
    public void makePayment(int x) {
        System.out.println("Payment Done through CreditCard in Rs :" + x);
    }
}

class AdaptPaypal implements Payment {

    private final Paypal paypal;

    public AdaptPaypal() {
        this.paypal = new Paypal();
    }

    @Override
    public void makePayment(int x) {
        paypal.makePaymentInDollar(x / 100);
    }
}

class PaymentModeStrategy {

    public static Map<PAYMENT_MODE, Payment> paymentInstance = new HashMap<>();

    void addNewPaymentInstance(PAYMENT_MODE mode, Payment instance) {
        paymentInstance.put(mode, instance);
    }

    private Payment payment;

    public PaymentModeStrategy(PAYMENT_MODE mode) {
        this.payment = paymentInstance.get(mode);
    }

    void updateStrategy(PAYMENT_MODE mode) {
        this.payment = paymentInstance.get(mode);
    }

    void makePayment(int x) {
        if (x < 1000 && payment instanceof AdaptPaypal) {
            System.out.println("Amt is too Less to Pay through Paypal Proceeding with UPI");
            updateStrategy(PAYMENT_MODE.UPI);
        }
        payment.makePayment(x);
    }


}

class Processpayment {

    private final PaymentModeStrategy mode;
    private final Validation requestValidate;
    private final PaymentRequest paymentRequest;

    public Processpayment(PAYMENT_MODE mode, Validation requestValidate, PaymentRequest paymentRequest) {
        this.mode = new PaymentModeStrategy(mode);
        this.requestValidate = requestValidate;
        this.paymentRequest = paymentRequest;
    }

    public void processPayment() throws IOException {
        this.requestValidate.ValidateAndExecute(this.paymentRequest);
        this.mode.makePayment(this.paymentRequest.amt());
    }
}

public class Question8 {

    public static void main(String[] args) throws IOException {
        PaymentRequest req = new PaymentRequest("Rajat", 1000, PAYMENT_MODE.UPI);
        addNewPaymentMethod(PAYMENT_MODE.UPI, new UPI());
        addNewPaymentMethod(PAYMENT_MODE.CREDIT_CARD, new CreditCard());
        addNewPaymentMethod(PAYMENT_MODE.PAYPAL, new AdaptPaypal());

        Validation fraudValidation = new Fraud();
        Validation amtValidation = new BalanceCheck();
        Validation validatePayment = new AuthenticationCheck();
        validatePayment.nextValidation(fraudValidation);
        fraudValidation.nextValidation(amtValidation);

        Processpayment payment = new Processpayment(PAYMENT_MODE.UPI, validatePayment, req);
        payment.processPayment();


    }

    static void addNewPaymentMethod(PAYMENT_MODE mode, Payment payment) {
        PaymentModeStrategy.paymentInstance.put(mode, payment);
    }
}
