package org.example.CDP;

interface PaymentGateWay{
    void printPaymentMethod();
}

class UPIPayment  implements  PaymentGateWay{

    public void printPaymentMethod(){
        System.out.println("Payment Done by UPIPayment");
    }
}

class CardPayment implements  PaymentGateWay{

    public void printPaymentMethod(){
        System.out.println("Payment Done by CardPayment");
    }
}

class PayPalPayment  implements  PaymentGateWay{

    public void printPaymentMethod(){
        System.out.println("Payment Done by PayPalPayment");
    }
}

class StripePayment implements  PaymentGateWay{

    public void printPaymentMethod(){
        System.out.println("Payment Done by StripePayment");
    }
}

interface Invoice{
    void isType();
}

class GSTInvoice implements  Invoice{
    public void isType(){
        System.out.println("Invoice Type is GST");
    }
}

class USInvoice implements  Invoice{
    public void isType(){
        System.out.println("Invoice Type is US");
    }
}

/*
This is Responsible to create combined business logic of all related object
 */
interface RegionFactory{
    PaymentGateWay createPaymentGateWay(String gateway);
    Invoice createInvoice();
}

class IndianPayment implements RegionFactory{

    public PaymentGateWay createPaymentGateWay(String gateway){
        if(gateway.equals("UPI"))
            return new UPIPayment();
        else
            return new CardPayment();
    }
    public  Invoice createInvoice(){

            return new GSTInvoice();
    }
}

class UsPayment implements RegionFactory{

    public PaymentGateWay createPaymentGateWay(String gateway){
            if(gateway.equals("paypal"))
                return  new PayPalPayment();
            else
                return new StripePayment();
    }
    public  Invoice createInvoice(){
        return new USInvoice();
    }
}

/*
 AbstractFactory Class creating instance based of the input and performing the calling the B logic
 */
class CheckoutService{

    private  PaymentGateWay paymentGateWay;
    private  Invoice invoice;
    String  paymentType;

    public  CheckoutService(RegionFactory factory,String paymentType){
        this.paymentGateWay = factory.createPaymentGateWay(paymentType);
        this.paymentType=paymentType;
        this.invoice = factory.createInvoice();
    }

    public  void completeOrder(){
        this.paymentGateWay.printPaymentMethod();
        this.invoice.isType();
    }
}



public class AbstractFactory {

    public  static  void main(String[] args){
        CheckoutService checkoutService = new CheckoutService(new IndianPayment(),"UPI");
        checkoutService.completeOrder();

        CheckoutService checkoutService1 = new CheckoutService(new IndianPayment(),"Card");
        checkoutService1.completeOrder();

        CheckoutService checkoutService2 = new CheckoutService(new UsPayment(),"paypal");
        checkoutService1.completeOrder();

        CheckoutService checkoutService3 = new CheckoutService(new UsPayment(),"stripe");
        checkoutService3.completeOrder();
    }
}
