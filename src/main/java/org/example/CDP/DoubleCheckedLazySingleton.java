package org.example.CDP;

public class DoubleCheckedLazySingleton {

    private static volatile DoubleCheckedLazySingleton instance;

    private DoubleCheckedLazySingleton(){

    }

    public static DoubleCheckedLazySingleton getInstance(){

        if(instance==null){
            synchronized (DoubleCheckedLazySingleton.class){
                if(instance==null){
                    instance= new DoubleCheckedLazySingleton();
                }
            }
        }
        return  instance;
    }
}


