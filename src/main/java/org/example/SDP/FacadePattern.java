package org.example.SDP;


/*
Consider a BookMyShow  feature : booking + payment + seat allotment
-- > ideally we should take all input at once and build instance on the basis of tha

 */

// Writing Simple Classes

class BookingDate{

    public  void isBooked(int n){
        System.out.println("Booking Date is "+ n);
    }
}

class PaymentMethod {

    public void paymentType(String str){
        System.out.println("Payment Done by" + str);
    }
}

class SeatAllotment {

    public void seatAlloted(String str){
        System.out.println("Seat Allotted is " + str);
    }
}

// MoviebookingFacade Class
//  Single Service Handing info from customer and redirecting as per the requirement
class MovieBookingFacade {

    private  PaymentMethod paymentMethod;
    private SeatAllotment seatAllotment;
    private  BookingDate bookingDate;

    public MovieBookingFacade(){
        this.paymentMethod= new PaymentMethod();
        this.bookingDate = new BookingDate();
        this.seatAllotment =new SeatAllotment();
    }

    public void bookTicket(String seat, int date , String paymentType){
        seatAllotment.seatAlloted(seat);
        paymentMethod.paymentType(paymentType);
        bookingDate.isBooked(date);

    }
}
public class FacadePattern {

    public static void main (String [] args){
        MovieBookingFacade movieBookingFacade = new MovieBookingFacade();
        movieBookingFacade.bookTicket("1D", Integer.parseInt("19"),"Online");

    }
}
