package org.example.CDP;

public class BillPughSingleton {

    private  BillPughSingleton(){

    }

    private  static  class Holder{
        private static final BillPughSingleton instance = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return Holder.instance;
    }
}
