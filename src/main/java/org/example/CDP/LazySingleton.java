package org.example.CDP;

public class LazySingleton {

    private static LazySingleton lazySingleton;

    private  LazySingleton(){
        System.out.println("Instance Created of LazySingleton Class");
    }

    public  static LazySingleton getInstance(){
        if(lazySingleton==null)
            lazySingleton = new LazySingleton();
        else
            System.out.println("Instance was Already Created");
        return lazySingleton;
    }
}

