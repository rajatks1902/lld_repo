package rks.dev.LLD_CDP;

public class EagerSingleton {

    private static final EagerSingleton singleInstance = new EagerSingleton();

    private EagerSingleton() {
        System.out.println("Singleton Class Instance Created");
    }

    public static EagerSingleton getInstance() {
        return singleInstance;
    }

}
