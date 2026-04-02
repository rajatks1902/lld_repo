package rks.dev.LLD_PracticeQuestion;

public class OptimisedSingleton {

    private OptimisedSingleton() {
    }


    private static class InnerClass {

        private static final OptimisedSingleton op = new OptimisedSingleton();

    }

    public static OptimisedSingleton getInstance() {
        return InnerClass.op;
    }
}
