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
public class Question7 {
}
