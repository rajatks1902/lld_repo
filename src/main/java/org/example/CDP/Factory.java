package org.example.CDP;

interface  Travel {

    void way();

    void cost();

    boolean isSafe();
}
class Air implements  Travel{

    @Override
    public void way() {
        System.out.println("Traveling by Air");
    }

    @Override
    public void cost() {
        System.out.println("High Cost Involved");
    }

    @Override
    public boolean isSafe() {
        return false;
    }
}

class Train implements  Travel{

    @Override
    public void way() {
        System.out.println("Traveling by Train");
    }

    @Override
    public void cost() {
        System.out.println("Moderate  Cost Involved");
    }

    @Override
    public boolean isSafe() {
        return true;
    }
}


/*
 Factory Class responsible to create the instance based on the input
 creates a full wrapper helping in easy creation of instance without much fuss
 */
class TravelManager{

    public static Travel checkDetails(String mode){

        if(mode.equals("Air"))
            return new Air();
        else
            return new Train();

    }
}


/*
Business Logic class where the instance of Travel is created and operation is done.
 */
class TravelHandler {
    public void send(String mode){
        Travel manager =  TravelManager.checkDetails(mode);
        manager.cost();
        manager.way();
    }
}


public class Factory {

    public  static  void main(String args[]){
        TravelHandler handler = new TravelHandler();
        handler.send("Train");



    }
}
