### I am using GRPC, Kafka, Redis and Postgres in this project to develop my skills

# 📊 Log Processing System

A distributed system for real-time log processing built with modern technologies to enhance backend development skills.

## 🏗 System Architecture

### Technology Stack
- **gRPC** - for inter-service streaming communication
- **Apache Kafka** - for asynchronous message brokering
- **Redis** - for caching and fast data access
- **PostgreSQL** - for persistent data storage
- **REST API** - for external client interactions

## 🎯 Project Structure

### Services Overview

#### 1. 📁 File Watcher Service
**Purpose**: Monitor file system for new log files

**Features**:
- Watches `/logs` directory for new file creation
- Detects and processes log files in real-time
- Publishes log data to Kafka topic `logs`

#### 2. 🔄 File Processor Data
**Purpose**: Process streaming log data

**Features**:
- Consumes messages from Kafka topic `logs`
- Processes and transforms log data
- Streams processed logs via gRPC

#### 3. 🌐 API Gateway
**Purpose**: Unified entry point and data management

**Features**:
- Receives logs via gRPC streaming
- Maps and transforms data structures
- Persists data to PostgreSQL
- Caches frequently accessed data in Redis
- Provides REST API endpoints

## 🔄 Data Flow

```mermaid
graph LR
    A[📁 Log Files] --> B[🕵️ File Watcher]
    B --> C[📨 Kafka logs Topic]
    C --> D[🔄 File Processor]
    D --> E[📡 gRPC Stream]
    E --> F[🌐 API Gateway]
    F --> G[💾 PostgreSQL]
    F --> H[🗄️ Redis Cache]
    F --> I[🖥️ REST API]
    I --> J[👤 Clients]
