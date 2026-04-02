package rks.dev.LLD_PracticeQuestion;

/*
=========================================================
Problem 1: Payment Gateway Integration
Category: Structural Pattern (Identify Yourself)
=========================================================

Problem Statement:

You are building a Payment Service in your application.

Your system expects all payment providers to follow this interface:

interface PaymentProcessor {
    void pay(int amount);
}

-----------------------------------------------
Existing System:

Your application uses:
- PaymentProcessor interface

-----------------------------------------------
Third-Party Integration:

You need to integrate a third-party library:

class RazorpayAPI {
    void makePayment(double amountInRupees) {
        System.out.println("Paid via Razorpay: " + amountInRupees);
    }
}

-----------------------------------------------
Problem:

- RazorpayAPI does NOT follow your PaymentProcessor interface
- You CANNOT modify RazorpayAPI (third-party)

-----------------------------------------------
Task:

1. Integrate RazorpayAPI into your system
2. Make sure client code still uses:
   PaymentProcessor interface ONLY
3. Do NOT modify existing interfaces/classes

-----------------------------------------------
Client Usage:

PaymentProcessor payment = ...;
payment.pay(100);

-----------------------------------------------
Follow-up:

1. What pattern did you use?
2. How is this different from Strategy pattern?
3. Can this support multiple third-party providers?

=========================================================
*/
interface PaymentGateway {
    void pay(String orderId, double amount);
}

/*
-----------------------------------------------
Concrete Implementation (Internal System)
-----------------------------------------------
*/
class UPIPayment implements PaymentGateway {

    @Override
    public void pay(String orderId, double amount) {
        System.out.println("UPI Payment | Order: " + orderId + " | Amount: " + amount);
    }
}

/*
-----------------------------------------------
Third-Party Class (Cannot Modify)
-----------------------------------------------
*/
class StripePayment {

    public void paymentAmount(double amount) {
        System.out.println("Stripe Payment Amount: " + amount);
    }

    public void paymentId(String id) {
        System.out.println("Stripe Payment Id: " + id);
    }
}

/*
-----------------------------------------------
Adapter Class
-----------------------------------------------
- Converts StripePayment → PaymentGateway
- Maps incompatible interface
-----------------------------------------------
*/
class StripePaymentAdapter implements PaymentGateway {

    private final StripePayment stripePayment;

    // Dependency Injection (Better for testing & flexibility)
    public StripePaymentAdapter(StripePayment stripePayment) {
        this.stripePayment = stripePayment;
    }

    @Override
    public void pay(String orderId, double amount) {
        // Adaptation logic
        stripePayment.paymentAmount(amount);
        stripePayment.paymentId(orderId);
    }
}

/*
-----------------------------------------------
Business Layer
-----------------------------------------------
- Depends ONLY on abstraction
- No knowledge of third-party APIs
-----------------------------------------------
*/
class CheckOutService {

    private final PaymentGateway paymentGateway;

    public CheckOutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String orderId, double amount) {
        paymentGateway.pay(orderId, amount);
    }
}

/*
-----------------------------------------------
Client Code
-----------------------------------------------
*/
public class Question4 {

    public static void main(String[] args) {

        // Inject dependency
        StripePayment stripe = new StripePayment();
        PaymentGateway paymentGateway = new StripePaymentAdapter(stripe);

        CheckOutService service = new CheckOutService(paymentGateway);

        service.checkout("ORD-101", 250.75);

        /*
        -----------------------------------------------
        Easily extendable for other providers
        -----------------------------------------------
        Example:
        PaymentGateway razorpay = new RazorpayAdapter(new RazorpayAPI());
        */
    }
}
