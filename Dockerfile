# Используем публичный образ OpenJDK 18
FROM registry.access.redhat.com/ubi8/openjdk-18:latest


# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем весь проект
COPY . .

# Сборка проекта через Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Указываем jar для запуска
CMD ["java", "-jar", "target/pocketgpt-0.0.1-SNAPSHOT.jar"]
