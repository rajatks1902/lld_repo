package org.example;

class NestedNonStatic
{
    static  int ans =10;
    String name ="Rajat";

    static boolean isTrue(){
        return  true;
    }

    void isNonStatic(){
        System.out.println("Should  be accessed by non static inner classes");
    }

    class InnerClass {

        public  void  execute(){
            System.out.println("Testing" + " " + ans  + " "+isTrue());
            System.out.println("Testing " + name);
            isNonStatic();
        }
    }

}

public class NonStaticNested {

    public static  void main (String[] args) {
        // for instance of inner class , instance of outer class is required
        NestedNonStatic outerClass = new NestedNonStatic();
        NestedNonStatic.InnerClass test = outerClass.new InnerClass();
        test.execute();
    }


}
