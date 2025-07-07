# Use the official devcontainers Java 17 image (includes Maven)
FROM mcr.microsoft.com/devcontainers/java:17

# Verify Maven is available and install if needed
RUN apt-get update && apt-get install -y maven && \
    mvn --version && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# [Optional] Install additional tools here
# RUN apt-get update && apt-get install -y <package-name>
