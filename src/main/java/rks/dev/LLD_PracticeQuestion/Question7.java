package rks.dev.LLD_PracticeQuestion;

/*

Problem Statement:

You are building an API request processing system.

Each request must pass through multiple handlers:

1. AuthenticationHandler
   - Validates if user is authenticated

2. AuthorizationHandler
   - Checks if user has required role

3. LoggingHandler
   - Logs request details

-----------------------------------------------
Flow:

Request → Auth → Authorization → Logging → Final Processing

-----------------------------------------------
Requirements:

1. Each handler should:
   - Process request
   - Decide whether to pass to next handler

2. If any handler fails:
   - Stop the chain
   - Do NOT call next handler

3. Handlers should be easily:
   - Reordered
   - Extended
   - Added/removed

-----------------------------------------------
Design Constraint:

- Avoid if-else chains
- Follow Open/Closed Principle
- Use loose coupling

-----------------------------------------------
Example:

Request request = new Request("user", "ADMIN", true);

Handler chain = new AuthenticationHandler(
                    new AuthorizationHandler(
                        new LoggingHandler(null)
                    )
                );

chain.handle(request);

-----------------------------------------------
Follow-up Questions:

1. What pattern is this?
2. Where is this used in real systems?
3. Difference between Chain of Responsibility and Decorator?

=========================================================
*/


class Request {
    String user;
    String role;
    boolean authenticated;

    public Request(String user, String role, boolean authenticated) {
        this.user = user;
        this.role = role;
        this.authenticated = authenticated;
    }
}

/*
-----------------------------------------------
Abstract Handler
-----------------------------------------------
*/
abstract class Handler {

    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handle(Request request);
}

/*
-----------------------------------------------
Concrete Handlers
-----------------------------------------------
*/
class AuthenticationHandlerQ7 extends Handler {

    @Override
    public void handle(Request request) {
        if (!request.authenticated) {
            System.out.println("Authentication Failed");
            return; // stop chain
        }

        System.out.println("Authentication Passed");

        if (next != null) {
            next.handle(request);
        }
    }
}

class AuthorizationHandler extends Handler {

    @Override
    public void handle(Request request) {
        if (!"ADMIN".equals(request.role)) {
            System.out.println("Authorization Failed");
            return; // stop chain
        }

        System.out.println("Authorization Passed");

        if (next != null) {
            next.handle(request);
        }
    }
}

class LoggingHandler extends Handler {

    @Override
    public void handle(Request request) {
        System.out.println("Logging request for user: " + request.user);

        if (next != null) {
            next.handle(request);
        }
    }
}

/*
-----------------------------------------------
Client
-----------------------------------------------
*/
public class Question7 {

    public static void main(String[] args) {

        // Build chain
        Handler auth = new AuthenticationHandlerQ7();
        Handler authorization = new AuthorizationHandler();
        Handler logging = new LoggingHandler();

        auth.setNext(authorization);
        authorization.setNext(logging);

        // Create request
        Request request = new Request("Rajat", "ADMIN", true);

        // Execute chain
        auth.handle(request);
    }
}