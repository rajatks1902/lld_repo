package org.example.SDP;


/*
 Ex : Burger Can have multiple combination where items can be same for multiple burger
       -- Creating a different class for each subsection will be a huge overload
       --> each new item will have *n new combination
 */

interface  Pizza {
    String getDes();

    double getCost();
}

class PlainPizza implements Pizza{


    @Override
    public String getDes() {
        return "Plain Base";
    }

    @Override
    public double getCost() {
        return 40.0;
    }
}

class MargheritaPizza implements Pizza{

    @Override
    public String getDes() {
        return "Margherita Pizza";
    }

    @Override
    public double getCost() {
        return 90.0;
    }
}

abstract  class PizzaDecorator implements Pizza{

    protected  Pizza pizza;

    public PizzaDecorator(Pizza pizza){
        this.pizza = pizza;
    }

}

class  ExtraCheese extends  PizzaDecorator{


    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDes() {
        return  pizza.getDes() + ", Extra Cheese" ;
    }

    @Override
    public double getCost() {
        return  pizza.getCost() +40;
    }
}
public class DecoratorPattern {

    public static void main(String[] args){

        Pizza pizza = new MargheritaPizza();
        pizza = new ExtraCheese(pizza);
        System.out.println(pizza.getCost());

    }
}
