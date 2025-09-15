package org.example.SDP;

/*
    --> When we Want to wrap 2 different kind of object sharing  the same requirement as the end result we created a
        class which those classes will extends so that they can provide similar end result
        Ex : Product  && ProductGroup -- We want final price of both category with simple same method call

 */

import javax.smartcardio.Card;

interface  CartItem {
    double getPrice();
}

class Product implements CartItem{

    // Other functionality : like Calculating the final Amount of product

    @Override
    public double getPrice() {
        return 0;
    }
}

class ProductCombo implements CartItem{

    @Override
    public double getPrice() {
        return 100;
    }
}
/*
Now to Find the final Value we can have a common implementation
 */
public class CompositePattern {

    public  static  void main(String [] agrs){
        CartItem cartItem = new Product();
        CartItem cartItem1  = new ProductCombo();
        System.out.println(cartItem);
        System.out.println(cartItem1);
    }
}
