package org.example.CDP;

public class BillPughSingleton {

    private  BillPughSingleton(){

    }

    /*
    Static Inner Classes are not loaded in jvm when container is ups:
        -> It is a kind of lazy loading
        -> It loads only when you actually reference of inner class is called
        -> creating the instance static will mean only 1 instance will be created
     */
    private  static  class Holder{
        private static final BillPughSingleton instance = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return Holder.instance;
    }
}
