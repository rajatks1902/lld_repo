package org.example.CDP;

public class DoubleCheckedLazySingleton {

    // Volatile is imp to let other thread know that the instance should not be picked from cache
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


