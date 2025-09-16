package org.example.BDP;


/*
  --> We have multiple Algo to get more revenue
  ---> if Once Model do not work switching to another model at runtime
 */

interface  MatchingAlgo{
    void match ();
}

class Nearest implements  MatchingAlgo{

    public void match(){
        System.out.println("Nearest Rider Match");
    }
}

class Airport  implements  MatchingAlgo{

    public void match(){
        System.out.println("Airport Rider Match");
    }
}

class RiderMatchStrategy {
    private  MatchingAlgo matchingAlgo;

    public  RiderMatchStrategy(MatchingAlgo matchingAlgo){
        this.matchingAlgo = matchingAlgo;
    }

    public  void setMatchingAlgo(MatchingAlgo algo){
        this.matchingAlgo = algo;
    }
    public  void matchRider(){
        matchingAlgo.match();
    }
}
public class StrategyPattern {

    public static   void main(String[] args){
        RiderMatchStrategy riderMatchStrategy = new RiderMatchStrategy(new Airport());
        riderMatchStrategy.matchRider();;

        riderMatchStrategy.setMatchingAlgo(new Nearest());
        riderMatchStrategy.matchRider();
    }
}
