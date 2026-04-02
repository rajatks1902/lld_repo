package rks.dev.LLD_PracticeQuestion;

/*
=========================================================
Problem 2: Coffee Ordering System
Difficulty: 3-Year SDE
Category: Structural Pattern (Identify Yourself)
=========================================================

Problem Statement:

You are building a coffee ordering system.

Base coffee:
- SimpleCoffee → cost = 100

You need to support adding multiple add-ons:
- Milk → +20
- Sugar → +10
- WhippedCream → +30

-----------------------------------------------
Requirements:

1. You should be able to dynamically add add-ons:
   Example:
   Coffee coffee = new Milk(new Sugar(new SimpleCoffee()));

2. Total cost should be calculated correctly:
   Example:
   SimpleCoffee + Milk + Sugar = 130

3. You should NOT modify existing classes when adding new add-ons

4. All combinations should be supported:
   - Coffee + Milk
   - Coffee + Sugar + Milk
   - Coffee + Milk + Cream + Sugar
   etc.

-----------------------------------------------
Constraints:

- Follow Open/Closed Principle
- Avoid class explosion (no creating class for each combination)

-----------------------------------------------
Follow-up:

1. What pattern is this?
2. Why not use inheritance?
3. Difference between Decorator and Proxy?

=========================================================
*/
interface Coffee {
    String getDescription();

    int getCost();
}

/*
-----------------------------------------------
Base Component
-----------------------------------------------
*/
class BasicCoffee implements Coffee {

    @Override
    public String getDescription() {
        return "Coffee";
    }

    @Override
    public int getCost() {
        return 100;
    }
}

/*
-----------------------------------------------
Decorator Base Class
-----------------------------------------------
*/
abstract class CoffeeDecorator implements Coffee {

    protected final Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }

    @Override
    public int getCost() {
        return coffee.getCost();
    }
}

/*
-----------------------------------------------
Concrete Decorators
-----------------------------------------------
*/
class Milk extends CoffeeDecorator {

    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + Milk";
    }

    @Override
    public int getCost() {
        return coffee.getCost() + 20;
    }
}

class Sugar extends CoffeeDecorator {

    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + Sugar";
    }

    @Override
    public int getCost() {
        return coffee.getCost() + 10;
    }
}

/*
-----------------------------------------------
Client
-----------------------------------------------
*/
public class Question5 {

    public static void main(String[] args) {

        Coffee coffee = new BasicCoffee();

        coffee = new Milk(coffee);
        coffee = new Sugar(coffee);

        System.out.println(coffee.getDescription());
        System.out.println(coffee.getCost());
    }
}