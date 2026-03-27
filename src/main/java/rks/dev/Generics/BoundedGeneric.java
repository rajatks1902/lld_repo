package rks.dev.Generics;

class BoundedGenericEx<T extends Number> {

    T obj ;

    public void setObj(T obj) {
        this.obj = obj;
    }

    public  T getObj(){
        return this.obj;
    }

}

public class BoundedGeneric {

    public static void main(String args []){

        BoundedGenericEx<Integer> br = new BoundedGenericEx<>();


        // Compilation Error
        // BoundedGenericEx<String> br1 = new BoundedGenericEx<>();


    }
}
