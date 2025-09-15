package org.example.CDP;

import java.util.HashMap;
import java.util.Map;

interface  EmailTemplate extends Cloneable{

    EmailTemplate clone();

    void setHeader();

    void send(String mailId);
}

class WelcomeEmail implements EmailTemplate{

    @Override
    public EmailTemplate clone(){
        try{
            return (WelcomeEmail) super.clone();
        }catch (CloneNotSupportedException exp){
            throw new RuntimeException("Clone Failed" , exp);
        }
    }

    @Override
    public void setHeader() {
        System.out.println("Sending Welcome mail");
    }

    @Override
    public void send(String mailId) {
        System.out.println("Sending Email to " + mailId);
    }
}

class GoodBye implements EmailTemplate{

    @Override
    public GoodBye clone(){
        try{
            return (GoodBye) super.clone();
        }catch (CloneNotSupportedException exp){
            throw new RuntimeException("Clone Failed" , exp);
        }
    }

    @Override
    public void setHeader() {
        System.out.println("Sending GoodBye mail");
    }

    @Override
    public void send(String mailId) {
        System.out.println("Sending Email to " + mailId);
    }
}
/*
Create Single Instance of each Email templates in JVM during load
  -> When instance is needed we call the clone method of that type instance
  -> clone will create a deep copy :)
  -> Helps in Faster Order Creation
 */
class EmailTemplateRegistry{

    private static final Map<String,EmailTemplate> templates = new HashMap<>();

    static {
        templates.put("welcome",new WelcomeEmail());
        templates.put("goodbye", new GoodBye());
    }

    public  static EmailTemplate getTemplate(String type){
        return templates.get(type).clone();
    }
}


public class PrototypeImpl {

    public static void main (String[] args){
        EmailTemplate welcome = EmailTemplateRegistry.getTemplate("welcome");
        welcome.send("rajatsingh@gmail.com");
        welcome.setHeader();
    }
}
