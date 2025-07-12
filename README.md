# StockPulse 📈

StockPulse is a backend microservices-based app to monitor stock prices and user alerts. Built using **Spring Boot**, **Java**, **MySQL**, **JWT Auth**, and optionally **Redis** for caching.

## ⚙️ Tech Stack
- Java 17
- Spring Boot (Security, Web, Data JPA)
- MySQL
- JWT-based Auth
- Redis (Optional)
- Maven

## 🧠 Features
- User Registration & JWT Authentication
- Watchlist management
- Real-time stock data ingestion
- Custom alert triggers
- Modular microservices architecture

## 🛠 Modules
- `stock-service`: Manages stocks and watchlist
- `alert-service`: Handles alert scheduling (coming soon)

## 📂 Database Schema
Check [`/docs/db-schema.sql`](./docs/db-schema.sql) (or add schema info here)

## 🚀 Getting Started
```bash
git clone https://github.com/YOUR_USERNAME/stockpulse.git
cd stockpulse
mvn clean install
