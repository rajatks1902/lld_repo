package rks.dev.LLD_CDP;

public class DoubleCheckedLazySingleton {

    // Volatile is imp to let other thread know that the instance should not be picked from cache
    // volatile Keyword is to let all the thread know the state of the current key
/*
Object Creation is a 3-Step process
 -:Allocate memory
 -:Assign reference to instance ❗
 -:Run constructor


What can go wrong?
 Thread 1:
  Enters synchronized block
  Starts creating object
  Reordering happens → assigns reference early
 Thread 2:
  Calls getInstance()
  Sees instance != null ✅
  Skips synchronization
  Uses object ❌

  Result  : Thread 2 gets a half-initialized object
  JVM can reorder steps like this:
        Memory allocation
        Reference assignment ❗
        Constructor execution
 */


    private static volatile DoubleCheckedLazySingleton instance;

    private DoubleCheckedLazySingleton() {

    }

    public static DoubleCheckedLazySingleton getInstance() {

        if (instance == null) {
            synchronized (DoubleCheckedLazySingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLazySingleton();
                }
            }
        }
        return instance;
    }
}


