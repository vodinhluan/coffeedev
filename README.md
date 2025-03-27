# CoffeeDev - Order Management System

## Overview

CoffeeDev is a comprehensive order management system designed to handle customer orders efficiently. It features a robust backend built with Spring Boot and a modern frontend developed with React and TypeScript.

## Features

- User-friendly order management interface
- Secure authentication and authorization
- RESTful API for seamless frontend-backend communication
- Role-based access control for admins and users
- Integrated database support for order storage and retrieval

## Tech Stack

### Backend

- **Spring Boot** - RESTful API development
- **Spring Security** - Authentication & authorization
- **Hibernate & JPA** - Database management
- **MySQL / PostgreSQL** - Persistent storage

### Frontend

- **React** - Modern UI development
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool for frontend development
- **Recoil** - State management
- **Tailwind CSS** - Styling framework

## API Endpoints

### General Structure

The system follows RESTful principles, with API endpoints structured as follows:

- `GET /api/{resource}` - Retrieve a list of resources
- `GET /api/{resource}/{id}` - Retrieve a specific resource by ID
- `POST /api/{resource}` - Create a new resource
- `PUT /api/{resource}/{id}` - Update an existing resource
- `DELETE /api/{resource}/{id}` - Delete a resource

### Example: Orders API

- `GET /api/orders?page={pageNum}&sort={field}&order={asc|desc}` - Fetch paginated orders
- `GET /api/orders/{id}` - Fetch order details
- `POST /api/orders` - Create a new order
- `PUT /api/orders/{id}` - Update order details
- `DELETE /api/orders/{id}` - Remove an order

## Getting Started

### Prerequisites

Ensure you have the following installed:

- JDK 17+
- Node.js 16+
- MySQL/PostgreSQL database

### Backend Setup

```sh
# Navigate to backend directory
cd backend

# Configure database in application.properties
# Run the application
./mvnw spring-boot:run
```

### Frontend Setup

```sh
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start the development server with Vite
npm run dev
```

## Contributing

1. Fork the repository
2. Create a new branch (`feature/your-feature`)
3. Commit your changes
4. Push the branch and create a PR

## License

This project is licensed under the MIT License.