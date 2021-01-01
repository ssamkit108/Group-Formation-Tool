# Group Formation Tool

The group formation tool is developed using Java, Spring Boot, Thymleaf and MySQL with high adherence to S.O.L.I.D., cohesion, and coupling principles. The codebase uses abstract factory creational pattern along with error handling, logging, and clean code practices.


## Implementation
### **S.O.L.I.D Principles** and **Design Patterns**
- Each and every class has specific one responsibility that defines the component of the implemented features.
- No use of hard coded values.
- Database credentials are stored as environment variables for security purpose and not in the source code.
- Production code contains no layer violation, which is useful when dealing with modifications in existing functionality.
- _Programming to an interface_ can be observed in production code, which helps in reducing coupling among different modules.
- All the packages contain java files that represent specific functionality and hence high cohesion among those files are necessary.
- Creational design pattern is used to instantiate classes. To instantiate classes, abstract factories are used, whose implementation supplied concrete factories for using business logic.
- Behavioural design pattern is also used to manage the algorithm for forming the groups. State pattern helps in switching between different algorithms and hence provides the flexibility to adopt new algorithm whenever requried without changing more code.

### **Test Driven Development (TDD)**
- For developing high quality and robust system that never fails, TDD approach fits best. For writing test cases, various mock objects are created. The library used for these test cases is JUnit.
- Test cases cover all the business logics that are written in the production code.
- Test code adheres to the same clean code standards as production code.

### **Continuous Integration and Continuous Deployment Pipeline**
- During the entire development cycle, gitflow was maintained by creating master, develop and other feature branches. Based on the change in branches, workflow was determined, which can be observed in [.group.yml](https://github.com/karankharecha/GroupFormationTool/blob/master/.group.yml) file.
- Test cases are executed by the runner when the code is changed in master and develop branch.
- Environments and databases: Dev, Test and Production.

### **Logging and Exception handling**
- This project contains logs of all the major events that were printed on Heroku's console

### **Clean Code**
- Consistent indentation
- Language (standards) idioms
- Preferred polymorphism to conditions
- No negative conditionals
- No comment noise
- Resource clean up after usage
