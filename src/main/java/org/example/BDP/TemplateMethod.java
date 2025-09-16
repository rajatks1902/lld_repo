package org.example.BDP;

/*
 Standard BluePrint Class that multiple Services follows
       --> Structure remains same
       --> internal mechanism differ for each services
 */

abstract  class NotificationSender {

    // making final so that the template format is not overridden
    public final void send(String content){
         check(content);
         sending(content);
    }

    // Generic to all Notification
    private void check(String content){
        System.out.println("checking the content" + content);
    }

    protected  abstract void sending(String content);
}

class EmailNotification extends NotificationSender{

    @Override
    protected void sending(String content) {
        System.out.println("Sending EmailNotification to External Client" + content);
    }
}
public class TemplateMethod {

    public  static void main(String[] args){
        NotificationSender notificationSender = new EmailNotification();
        notificationSender.send("Template Pattern");
    }
}
