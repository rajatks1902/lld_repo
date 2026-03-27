package rks.dev.CDP;

public class DoubleCheckedLazySingleton {

    // Volatile is imp to let other thread know that the instance should not be picked from cache
    // volatile Keyword is to let all the thread know the state of the current key
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


