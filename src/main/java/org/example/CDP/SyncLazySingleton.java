package org.example.CDP;

public class SyncLazySingleton {

    private static SyncLazySingleton SyncLazySingleton;

    private  SyncLazySingleton(){
        System.out.println("Instance Created of LazySingleton Class");
    }

    public  static synchronized SyncLazySingleton getInstance(){
        if(SyncLazySingleton ==null)
            SyncLazySingleton = new SyncLazySingleton();
        else
            System.out.println("Intance was Already Created");
        return SyncLazySingleton;
    }
}
