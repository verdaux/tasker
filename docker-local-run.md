5. Build the Application
Run:
./mvnw clean package
You should get:
target/task-api.jar
6. Start Everything
Run:
docker compose up --build
What happens:
1️⃣ PostgreSQL container starts
2️⃣ Health check runs
3️⃣ Spring Boot container starts
4️⃣ API connects to DB
7. Verify Containers
Run:
docker ps
Expected:
task-postgres
task-api
8. Test Your API
Open:
http://localhost:8080
Example endpoint:
http://localhost:8080/api/tasks
9. Check Logs
If something fails:
docker compose logs -f
Or for only API:
docker compose logs task-api
10. Stop Everything
docker compose down
Delete database too:
docker compose down -v
