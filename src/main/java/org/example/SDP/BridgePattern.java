package org.example.SDP;

/*
2 Interface have to interact ,  they should be dependent on each other instance or type
may be in future we might get new type of one instance , other interface should smoothly interact in this case also
So we Create A bridge i.e a Abstract Class making both interface independent.
-- Ex : [TV | Mobile ]  -- [HD , 4K ]  ----> now [TV | Mobile | Ipad ] --- [HD , 4K]
 */

interface ScreenType{

    void isType();
}

class HDScreen implements  ScreenType{

    @Override
    public void isType() {
        System.out.println("is HD");
    }
}
class K4Screen implements  ScreenType{

    @Override
    public void isType() {
        System.out.println("is 4K");
    }
}


abstract  class Player{
     protected  ScreenType screenType;

     public Player(ScreenType screenType){
         this.screenType=screenType;
     }
     public  abstract void detail(String detail);
}

class MobilePlayer extends Player{

    public MobilePlayer(ScreenType screenType) {
        super(screenType);
    }

    @Override
    public void detail(String detail) {
        screenType.isType();
    }
}

class TVPlayer extends Player{

    public TVPlayer(ScreenType screenType) {
        super(screenType);
    }

    @Override
    public void detail(String detail) {
        screenType.isType();
    }
}
public class BridgePattern {

    public static void main(String[] args) {
        Player player = new TVPlayer(new K4Screen());
        player.detail("Hi");

        Player player1 = new Ipad(new K4Screen());
        player1.detail("ipad info");
    }

}

// Now Adding another Player Will not impact the implementation of ScreenType

class Ipad extends Player{

    public Ipad(ScreenType screenType) {
        super(screenType);
    }

    @Override
    public void detail(String detail) {
        screenType.isType();
        System.out.println(detail);
    }
}
