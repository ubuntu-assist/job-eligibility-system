# Job Eligibility Evaluation System

A professional Java application that evaluates job eligibility based on candidate possessions. This system implements the **Composite pattern** to handle complex requirement hierarchies with AND/OR logic.

## 🎯 Problem Statement

Given a candidate with specific possessions (bike and driving license), determine which companies they can work for from a list of 10,000 companies, each with different job requirements.

### Example Requirements:

- **Company A**: requires an apartment OR house AND property insurance
- **Company B**: requires 5-door car OR 4-door car AND driving license AND car insurance
- **Company C**: requires social security number AND work permit
- **Company J**: no requirements (can work immediately)
- **Company K**: requires PayPal account

## 🏗️ Architecture

This solution follows **enterprise-grade** design patterns and principles:

### Design Patterns Used:

- **Composite Pattern**: For complex requirement hierarchies
- **Strategy Pattern**: For different types of requirements
- **Repository Pattern**: For data access abstraction
- **Service Layer Pattern**: For business logic encapsulation

### Package Structure:

```
src/main/java/com/jobeligibility/
├── domain/                    # Domain models
│   ├── Requirement.java       # Interface for requirements
│   ├── SimpleRequirement.java # Single item requirements
│   ├── CompositeRequirement.java # Complex AND/OR requirements
│   └── Company.java          # Company entity
├── service/                   # Business logic
│   └── JobEligibilityService.java
├── repository/                # Data access
│   └── CompanyRepository.java
└── JobEligibilityApplication.java # Main application
```

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ubuntu-assist/job-eligibility-system.git
   cd job-eligibility-system
   ```

2. **Compile and run:**

   ```bash
   mvn clean compile
   mvn exec:java -P run
   ```

3. **Run tests:**

   ```bash
   mvn test
   ```

4. **Build JAR:**
   ```bash
   mvn clean package
   java -jar target/job-eligibility-system-1.0.0.jar
   ```

## 📊 Sample Output

```
=== Job Eligibility Evaluation System ===

Candidate Possessions: [bike, driving license]
Total Companies: 10

=== ELIGIBILITY RESULTS ===
Company A     : ✗ CANNOT WORK
Company B     : ✗ CANNOT WORK
Company C     : ✗ CANNOT WORK
Company D     : ✗ CANNOT WORK
Company E     : ✗ CANNOT WORK
Company F     : ✓ CAN WORK
Company G     : ✗ CANNOT WORK
Company H     : ✗ CANNOT WORK
Company J     : ✓ CAN WORK
Company K     : ✗ CANNOT WORK

=== SUMMARY ===
Eligible Companies: 2
Ineligible Companies: 8
Success Rate: 20.0%
```

## 🔧 Technical Features

### 1. **Flexible Requirement Modeling**

- **Simple Requirements**: Single items (e.g., "bike", "driving license")
- **Composite Requirements**: Complex AND/OR combinations
- **Null Requirements**: Companies with no requirements

### 2. **Case-Insensitive Matching**

- Possessions are normalized to lowercase for comparison
- Handles variations in input format

### 3. **Comprehensive Validation**

- Input validation with meaningful error messages
- Null safety checks throughout the codebase

### 4. **Extensible Design**

- Easy to add new requirement types
- Simple to extend with new companies
- Repository pattern allows for different data sources

### 5. **Professional Testing**

- Comprehensive unit tests with JUnit 5
- Test coverage for edge cases
- Clear test naming and documentation

## 🧪 Testing

The project includes comprehensive unit tests covering:

- ✅ Basic eligibility evaluation
- ✅ Complex AND/OR requirement logic
- ✅ Edge cases (empty possessions, null inputs)
- ✅ Case-insensitive matching
- ✅ Service layer functionality

Run tests with:

```bash
mvn test
```

## 📈 Performance

- **Time Complexity**: O(n × m) where n = number of companies, m = average requirement complexity
- **Space Complexity**: O(n) for storing results
- **Scalability**: Can handle 10,000+ companies efficiently
- **Memory Efficient**: Uses immutable objects and streams

## 🎨 Code Quality

### Senior Developer Standards:

- **Clean Code**: Meaningful names, small methods, clear structure
- **SOLID Principles**: Single responsibility, open/closed, dependency inversion
- **Documentation**: Comprehensive JavaDoc for all public APIs
- **Error Handling**: Proper exception handling with meaningful messages
- **Immutability**: Final fields and immutable collections where appropriate
- **Type Safety**: Strong typing throughout the codebase

### Code Metrics:

- **Cyclomatic Complexity**: Low (simple, readable logic)
- **Test Coverage**: High (comprehensive test suite)
- **Documentation**: Complete (JavaDoc for all public methods)

## 🔄 Extending the System

### Adding New Companies:

```java
Company newCompany = new Company("New Company",
    new CompositeRequirement(CompositeRequirement.LogicalOperator.AND,
        new SimpleRequirement("requirement1"),
        new SimpleRequirement("requirement2")
    ));
```

### Adding New Requirement Types:

```java
public class CustomRequirement implements Requirement {
    @Override
    public boolean isSatisfiedBy(Set<String> possessions) {
        // Custom logic here
        return true;
    }

    @Override
    public String getDescription() {
        return "Custom requirement description";
    }
}
```

## 📝 License

This project is created as a technical assessment solution. Feel free to use and modify as needed.

## 👨‍💻 Author

**Fopa Kuete Duclair** - Technical Assessment Solution

---

_This solution demonstrates enterprise-grade Java development practices, clean architecture, and professional coding standards._
