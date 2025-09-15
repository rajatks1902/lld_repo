package org.example.CDP;

import java.util.Date;
import java.util.UUID;

public class BuilderClassOrder {


    private final UUID orderId;

    private  final String userName;

    private  final String email;

    private final String age;

    private  final Date date;

    //Here OrderBuilder is Used to Build Instance value of the required/optional fields
    public BuilderClassOrder(OrderBuilder orderBuilder){
        this.orderId=orderBuilder.orderId;
        this.userName=orderBuilder.userName;
        this.email=orderBuilder.email;
        this.age=orderBuilder.age;
        this.date=orderBuilder.date;
    }

    public static class OrderBuilder{

        // Compulsory Field So expecting them while OrderBuilder Creation
        private final UUID orderId;

        // Compulsory Field So expecting them while OrderBuilder Creation
        private  final String userName;

        // Depends on the requirement so using setter method and returning current instance
        private String email;

        // Depends on the requirement so using setter method and returning current instance
        private String age;

        // Depends on the requirement so using setter method and returning current instance
        private Date date;

        public  OrderBuilder(UUID orderId , String userName){
            this.orderId=orderId;
            this.userName=userName;
        }

        public OrderBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public OrderBuilder withAge(String age){
            this.age=age;
            return this;
        }

        public OrderBuilder withDate(Date date){
            this.date=date;
            return this;
        }

        // Builds the instance of Main Class i.e BuilderClassOrder
        public BuilderClassOrder build(){
            return new BuilderClassOrder(this);
        }
    }
}
