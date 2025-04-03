# =====================================
# STAGE 1 — Сборка приложения с Gradle
# =====================================
FROM gradle:8.5.0-jdk21-alpine AS build

WORKDIR /app

# Сначала копируем файлы для кэширования зависимостей
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Обязательно копируем buildSrc
COPY buildSrc ./buildSrc

# Кэшируем зависимости
RUN gradle dependencies --no-daemon

# Копируем оставшиеся исходники
COPY . .

# Собираем bootJar
RUN gradle bootJar --no-daemon

# =====================================
# STAGE 2 — Финальный образ с JRE
# =====================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Явно копируем единственный jar (без glob)
COPY --from=build /app/build/libs/geo-geodata-service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]