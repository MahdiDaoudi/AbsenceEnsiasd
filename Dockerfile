FROM openjdk:23-jdk-slim

# Installer les dépendances nécessaires pour exécuter JavaFX
RUN apt-get update && apt-get install -y \
    libopenjfx-java \
    && rm -rf /var/lib/apt/lists/*

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré dans l'image Docker
COPY target/absence-1.0-SNAPSHOT.jar /app/absence.jar

# Définir le point d'entrée pour exécuter l'application avec JavaFX
ENTRYPOINT ["java", "-cp", "/app/absence.jar:/usr/share/java/openjfx/*", "absence.AbsenceApp"]
