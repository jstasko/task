# TASK App (Java 21)
## 📜 Description
This is a lightweight Java 21 application that demonstrates the **Producer–Consumer** pattern using a **FIFO queue**, executing commands to manage user records in an **embedded H2 database**. The application includes:

- DTOs, mappers, services, and repositories
- Command pattern for operations (Add, PrintAll, DeleteAll)
- Java 21 records and features
- Proper exception handling and logging
- Configurable via `app.properties`
- Unit and integration tests using **JUnit 5 + AssertJ**
- Containerized using Docker

---

## ⚙️ Requirements
- Java 21+
- Maven 3.9+
- Docker & Docker Compose (optional for containerized run)

---

## 📦 How to Build
```bash
mvn clean package
```
> Generates a fat jar: `target/task-1.0-SNAPSHOT.jar`

---

## 🚀 How to Run

### 🔧 Locally (from terminal)
```bash
java -jar target/task-1.0-SNAPSHOT.jar
```

### 🐳 With Docker Compose
```bash
docker-compose up --build
```

This uses:
- `Dockerfile` to containerize the app
- `docker-compose.yml` for mounting configuration and data volumes

---

## 🐳 Running from Docker Hub
Once the image is pushed to Docker Hub, you can run it anywhere:

### 🧲 Pull the image
```bash
docker pull <your-dockerhub-username>/<image-name>:latest
```

### ▶️ Run the image
```bash
docker run --rm <your-dockerhub-username>/<image-name>:latest

## 🧪 Running Tests
```bash
mvn test
```
- Unit tests: `UserServiceTest`
- Integration tests: `UserIntegrationTest`

---

## ⚙️ Configuration (`src/main/resources/app.properties`)
```properties
jdbc.url=jdbc:h2:mem:testdb
db.init.script=init.sql
```

---

## 🏗️ How the App Works
1. **Producer** puts commands (`ADD`, `PRINT_ALL`, `DELETE_ALL`) into a FIFO `BlockingQueue`
2. **Consumer** thread pulls commands and dispatches them to appropriate **CommandHandlers**
3. Commands are handled via service layer → repository → embedded DB
4. `PRINT_ALL` outputs users to console

Example Command Sequence:
```text
ADD (1, "a1", "Robert")
ADD (2, "a2", "Martin")
PRINT_ALL
DELETE_ALL
PRINT_ALL
```

---

### 📄 Workflow location
Save this file as:
```bash
.github/workflows/docker-publish.yml
```

### 🔐 Required GitHub Secrets
In your repo settings → **Secrets and variables → Actions**, add:
- `DOCKER_USERNAME`: your Docker Hub username
- `DOCKER_TOKEN`: your Docker Hub access token (created at https://hub.docker.com/settings/security)



### ▶️ How to trigger the workflow
Go to your GitHub repo → **Actions tab** → select "Build and Push Docker Image" → click **Run workflow**.



## 🛠 Structure Overview
```
├── Main.java
├── dto/            # Data Transfer Objects
├── model/          # Database Entities
├── mapper/         # DTO ↔ Entity Mappers
├── command/        # Command Pattern Interfaces
├── service/        # Business Logic
├── repository/     # DB Access Layer
├── infra/          # Producer / Consumer logic
├── config/         # App configuration, SQL loader
├── util/           # Constants, Logging,
├── test/           # Unit and Integration Tests
├── app.properties  # Config file
├── init.sql        # Database schema
├── Dockerfile      # Container config
└── docker-compose.yml
```

---

## 🧾 License
This project is open-source and provided for educational/demo purposes.

---

For questions, feel free to reach out or open an issue!