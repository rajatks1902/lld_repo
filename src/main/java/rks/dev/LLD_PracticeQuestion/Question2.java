package rks.dev.LLD_PracticeQuestion;

/*
===============================================
Problem 2: Build a Complex User Object
Pattern: Builder Design Pattern
===============================================

Problem Statement:
Design a User class where object creation is flexible and readable,
especially when there are many optional parameters.

-----------------------------------------------
Attributes:

Required Fields:
- userId (String)
- name (String)

Optional Fields:
- email (String)
- phone (String)
- address (String)
- age (int)

-----------------------------------------------
Problem with Traditional Approach:

- Using constructors with many parameters leads to:
  - Poor readability
  - Hard to maintain
  - Error-prone (wrong parameter order)

-----------------------------------------------
Requirements:

1. Make the User class:
   - Immutable (all fields should be final)
   - No setters

2. Provide a private constructor in User class
   that takes a Builder object.

3. Create a static inner Builder class:
   - Should have same fields as User
   - Required fields must be passed via Builder constructor
   - Optional fields should have setter-like methods

4. Implement method chaining (Fluent API):
   Example:
   User user = new User.Builder("1", "Rajat")
                    .email("abc@gmail.com")
                    .phone("9999999999")
                    .age(25)
                    .build();

5. Implement build() method:
   - Should return User object
   - Can include validation (e.g., age >= 0)

-----------------------------------------------
Constraints / Expectations:

- Follow immutability strictly
- No telescoping constructors
- Fluent and readable API
- Validation logic inside build()
- Code should be extensible and clean

-----------------------------------------------
Follow-up Questions (Think after coding):

/*
=========================================================
Builder Pattern - Follow-up Answers (Improved)
=========================================================

1. How will you make this object thread-safe?

Answer:
- The final built object (User) is already thread-safe because:
  ✔ All fields are final
  ✔ No setters (immutable object)
  ✔ No shared mutable state

- Builder itself is NOT thread-safe and should NOT be made synchronized.
- Builder is intended for single-thread usage.

Best Practice:
"Ensure immutability of the final object instead of making Builder thread-safe.
Each thread should use its own Builder instance."

---------------------------------------------------------

2. Can Builder be reused after build()?

Answer:
- Technically YES, but NOT recommended.

Problem:
Builder b = new Builder("1", "Rajat");
User u1 = b.age(20).build();
User u2 = b.age(30).build(); // modifies previous state

- Leads to unintended state carryover and bugs.

Best Practice:
- Treat Builder as a one-time use object.
- Avoid reusing to maintain predictability.

---------------------------------------------------------

3. How would you add validation for email format?

Answer (Core Java):
if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
    throw new IllegalArgumentException("Invalid email");
}

- Validation should be done inside build() method.

Advanced (Framework-based):
- In Spring Boot, use Bean Validation:
  @Email annotation (Hibernate Validator)

---------------------------------------------------------

4. Where is Builder pattern used in real-world Java?

Answer:
- Lombok:
  @Builder annotation to generate builder automatically

- Spring Framework:
  UriComponentsBuilder

- Java Standard Library:
  StringBuilder, Stream.Builder

- Common Usage:
  - DTOs (Request/Response objects)
  - Complex object creation with many optional fields

---------------------------------------------------------

Key Takeaway:
- Builder Pattern ensures:
  ✔ Readability
  ✔ Immutability
  ✔ Safe object construction

========================================================
*/
class User {

    private final String userId;
    private final int age;
    private final String email;
    private final String phone;
    private final String address;
    private final String name;

    private User(Builder builder) {
        this.userId = builder.userId;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
        this.name = builder.name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {

        private final String userId;
        private final String name;

        private int age;
        private String email;
        private String phone;
        private String address;

        public Builder(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            if (age < 0) {
                throw new IllegalArgumentException("Age must be >= 0");
            }
            return new User(this);
        }
    }
}


public class Question2 {

    public static void main(String[] args) {
        User user = new User.Builder("07", "RKS").email("@nothing").build();

        System.out.println(user);
    }
}
