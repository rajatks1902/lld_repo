package rks.dev.LLD_PracticeQuestion;
/*
=========================================================
Problem 1: Multi-Type Notification System (Improved)
Patterns Used:
- Factory Pattern
- Singleton Pattern
- (Extensible for Composite / Strategy)
=========================================================
*/

import java.util.*;
import java.util.function.Supplier;

/*
-----------------------------------------------
Step 1: Common Interface
-----------------------------------------------
*/
interface Notification {
    void send(String message);
}

/*
-----------------------------------------------
Step 2: Concrete Implementations (Leaf Nodes)
-----------------------------------------------
*/
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Push: " + message);
    }
}

/*
-----------------------------------------------
Step 3: Enum for Type Safety
-----------------------------------------------
*/
enum NotificationType {
    EMAIL,
    SMS,
    PUSH
}

/*
-----------------------------------------------
Step 4: Singleton Factory with Registry
-----------------------------------------------
- Ensures only ONE factory instance
- Uses Map instead of switch (Open/Closed Principle)
- Supports dynamic registration
-----------------------------------------------
*/
class NotificationFactory {

    // Singleton instance (Eager Initialization)
    private static final NotificationFactory INSTANCE = new NotificationFactory();

    // Registry to map type -> object creator
    private final Map<NotificationType, Supplier<Notification>> registry = new HashMap<>();

    // Private constructor to prevent external instantiation
    private NotificationFactory() {
        // Default registrations
        registry.put(NotificationType.EMAIL, EmailNotification::new);
        registry.put(NotificationType.SMS, SMSNotification::new);
        registry.put(NotificationType.PUSH, PushNotification::new);
    }

    // Global access point
    public static NotificationFactory getInstance() {
        return INSTANCE;
    }

    /*
    -----------------------------------------------
    Factory Method
    -----------------------------------------------
    */
    public Notification getNotification(NotificationType type) {
        if (type == null || !registry.containsKey(type)) {
            throw new IllegalArgumentException("Invalid Notification Type");
        }
        return registry.get(type).get();
    }

    /*
    -----------------------------------------------
    Dynamic Registration (Open/Closed Principle)
    -----------------------------------------------
    - New types can be added WITHOUT modifying factory
    -----------------------------------------------
    */
    public void register(NotificationType type, Supplier<Notification> supplier) {
        registry.put(type, supplier);
    }
}

/*
-----------------------------------------------
Step 5 (Optional): Composite for Bulk Notifications
-----------------------------------------------
- Treat single & group uniformly
-----------------------------------------------
*/
class NotificationGroup implements Notification {

    private final List<Notification> notifications = new ArrayList<>();

    public void add(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public void send(String message) {
        for (Notification notification : notifications) {
            notification.send(message);
        }
    }
}

/*
-----------------------------------------------
Step 6: Client Code
-----------------------------------------------
*/
public class Question1 {

    public static void main(String[] args) {

        NotificationFactory factory = NotificationFactory.getInstance();

        // Single Notification
        Notification email = factory.getNotification(NotificationType.EMAIL);
        email.send("Hello Rajat");

        // Bulk using Composite
        NotificationGroup group = new NotificationGroup();
        group.add(factory.getNotification(NotificationType.EMAIL));
        group.add(factory.getNotification(NotificationType.SMS));

        System.out.println("---- Bulk Send ----");
        group.send("Bulk Message");

        /*
        -----------------------------------------------
        Adding New Type WITHOUT modifying factory
        -----------------------------------------------
        */
        factory.register(NotificationType.PUSH, PushNotification::new); // example

        // Example: Adding WhatsApp (if enum extended)
        // factory.register(NotificationType.WHATSAPP, WhatsAppNotification::new);
    }
}