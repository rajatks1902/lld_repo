package rks.dev.Generics;

class GenericEx<T> {

    T obj ;

    public void setObj(T obj) {
        this.obj = obj;
    }

    public  T getObj(){
        return this.obj;
    }

}
public class Generic {

    public  static  void main (String[] args){

        GenericEx<Integer> genericEx = new GenericEx<>();
        genericEx.setObj(100);
        System.out.println(genericEx.getObj());

        GenericEx<String> genericEx1 = new GenericEx<>();
        genericEx1.setObj("Messi");
        System.out.println(genericEx1.getObj());

        isGenericMethod("Mahi");
        isGenericMethod(7);
    }

    public  static <T>  void isGenericMethod (T obj){
        System.out.println("T in object of Type" + obj.getClass().getName());
    }
}
