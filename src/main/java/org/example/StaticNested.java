package org.example;

class Nested
{
    static  int ans =10;
    String name ="Rajat";

    static boolean isTrue(){
        return  true;
    }

    void isNonStatic(){
        System.out.println("Should not be accessed by static inner classes");
    }

    static  class Subclass{
        public  void  execute(){
            System.out.println("Testing" + ans  + isTrue());

            // innerClass will not access non-static variable of outer class same for the methods also
           // System.out.println("Testing " + name);
//            System.out.println("Testing " + isNonStatic());
        }
    }

}

public class StaticNested {

    public static  void main(String[] args){

        // no instance of outer class is Created
        Nested.Subclass innerClass  = new Nested.Subclass();
        innerClass.execute();
    }
}
