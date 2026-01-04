# 🚀 Pagila App

Pagila App is a full-stack development project built to experiment with a classic relational dataset using modern web technologies.

The application uses the **Pagila PostgreSQL dataset** as a data source and provides a backend API built with **Spring Boot** and a frontend interface developed with **React Ts**.

The project runs entirely in **Docker**, making it easy to start a complete development environment with a single command. It includes a PostgreSQL database container and a Spring backend container configured for **live development with hot reload**.

The React frontend allows browsing the dataset through a paginated interface, while the backend exposes REST endpoints to query the data.

# 🔴 Live Demo 🔴

Check out the live demo [here](https://mm9.dev)


## 🚀 Pagila App - Local Development Setup

This project provides a complete local development environment using **Docker Compose**.  
It runs:

- 🐘 **PostgreSQL** database
- ☕ **Spring Boot** backend
- ⚛️ **React (Vite)** frontend

The setup is optimized for **local development**, with **hot reload** and **live code changes** without restarting containers.

## ⚙️ Requirements

Make sure you have installed:

- 🐳  **Docker**
- 🐳  **Docker Compose**
- ☕ **Java 17**
- 📦 **npm**
- Gradle Wrapper (./gradlew already included)

## 🔧 Environment Configuration

The Docker environment uses environment variables from: `.env`

Before starting the project for the first time:
Copy the `.env.example` file and configure values in your `.env` file.

### 🌐 CORS Configuration

For local development, the frontend and backend run on different ports.

To support this, the Vite project includes a `.env` file with CORS settings see the `.env.example` file.

## 🐳 Starting the Full Environment

In the pagilafe folder run `npm install` and in pagilab folder run `./gradlew dockerDevPrepare` 

From the root directory run `docker compose up --build`

This starts:

- 🐘 PostgreSQL database

- ☕ Spring Boot backend

- ⚛️ React frontend


## 🙏 Credits

- PostgreSQL data model based on the Pagila dataset by [devrimgunduz](https://github.com/devrimgunduz/pagila), containerized with Docker by me (see [Docker image](https://ghcr.io/mm-97/pagila)) .
- External API used: [DiceBear](https://api.dicebear.com) for generating avatar images.
