package org.example.SDP;

/*
Structural Design Pattern are concerned with the composition of classes and object
    -> Focus is How to Assemble classes and object into larger Structure keeping
            these structures flexible and efficient.
 */

interface PaymentGateway {
    void pay(String orderId , double amt);
}

class UPIPayment implements PaymentGateway{

    @Override
    public void pay(String orderId, double amt) {
        System.out.println("Payment done through UPI");
    }
}

class StripePayment{

    public void paymentAmount(double amt){
        System.out.println("Payement amt is AMT");
    }

    public void paymentId(String id){
        System.out.println("Payment Id is xyz");
    }
}

/*
Since in our business logic we create instance of paymentGateway and StripePayment do not implement it
        --> We need to add a class that can adapt StripePayment
        --> Core Logic to map External Gateway to Adapt PaymentGateway
 */

class StripePaymentGateway implements PaymentGateway{

    private StripePayment stripePayment;

    public StripePaymentGateway(){
        this.stripePayment=new StripePayment();
    }

    @Override
    public void pay(String orderId, double amt) {
        stripePayment.paymentAmount(amt);
        stripePayment.paymentId(orderId);
    }
}

class CheckOutService{

    private PaymentGateway paymentGateway;

    public CheckOutService(PaymentGateway paymentGateway){
        this.paymentGateway=paymentGateway;
    }
    public  void checkout(String orderId, double amt){
        paymentGateway.pay(orderId,amt);
    }

}
public class AdapterPattern {

    public static void main(String[] args){
        CheckOutService service = new CheckOutService(new StripePaymentGateway());
        service.checkout("1",101.10);
    }
}
