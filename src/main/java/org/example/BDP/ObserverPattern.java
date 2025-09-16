package org.example.BDP;


/*
 Can be thought of notification triggering service once a event happens
 --> Ex : A notification of new YouTube Video Uploaded
 */

import java.util.ArrayList;
import java.util.List;

interface  Subscriber{
    void notify(String str);
}

class EmailSubscriber implements Subscriber{

    private final String email;

    public EmailSubscriber(String email){
        this.email=email;
    }


    @Override
    public void notify(String str) {
       System.out.println("Email Sent to " + str);
    }
}

class MobSubscriber implements Subscriber{

    private final String mobNotif;

    public MobSubscriber(String mobNotif){
        this.mobNotif =mobNotif;
    }


    @Override
    public void notify(String str) {
        System.out.println("mobNotif Sent to " + str);
    }
}

interface  Channel {
    void sub(Subscriber subscriber);
    void unSub(Subscriber subscriber);
    void notify(String str);
}

class YtChannel implements  Channel{

    private List<Subscriber> subscriberList = new ArrayList<>();
    private String channelName;

    public  YtChannel(String channelName){
        this.channelName=channelName;
    }
    @Override
    public void sub(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    @Override
    public void unSub(Subscriber subscriber) {
        subscriberList.remove(subscriber);

    }

    @Override
    public void notify(String str) {
            for(Subscriber subscriber : subscriberList ){
                subscriber.notify(channelName + str);
            }
    }
}
public class ObserverPattern {

    public static void main (String [] args) {

        Channel rks = new YtChannel("rks");
        rks.sub(new EmailSubscriber("Rajat Singh"));
        rks.sub(new MobSubscriber("Virat"));

        rks.notify("New Video Uploaded");

    }
}
