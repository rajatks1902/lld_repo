package org.example.BDP;


/*
  --> Tax Differ for diff kind of product : behavior different | end result same
  --> Amz Cart [ physical Product | Digital GiftCart ]
 */

import java.util.ArrayList;
import java.util.List;

interface  item {

    int price ();
    String  type();
    void accept(Customer customer);
}

class Physical implements item{

    @Override
    public int price() {
        return 99;
    }

    @Override
    public String type() {
        return "Physical";
    }

    @Override
    public void accept(Customer customer) {
        customer.visit(this);

    }
}

class Gift implements item{

    @Override
    public int price() {
        return 49;
    }

    @Override
    public String type() {
        return "GiftVoucher";
    }

    @Override
    public void accept(Customer customer) {
        customer.visit(this);

    }
}

interface Customer{
    void visit(Physical item);
    void visit(Gift gift);
}

class ItemType implements Customer{

    @Override
    public void visit(Physical item) {
        System.out.println(item.type());
    }

    @Override
    public void visit(Gift gift) {
        System.out.println(gift.type());

    }
}

class TotalCost implements Customer{

    @Override
    public void visit(Physical item) {
        System.out.println(item.price());
    }

    @Override
    public void visit(Gift gift) {
        System.out.println(gift.price());
    }
}
public class VisitorPattern {

    public static void main(String[] args){
        List<item> arr=  new ArrayList<>();
        arr.add(new Gift());
        arr.add(new Physical());
        Customer itemType= new ItemType();
        for(item i : arr){
          i.accept(itemType);
        }

    }
}
