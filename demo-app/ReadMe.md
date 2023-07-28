# Retail Application - Products Screen

This Spring Boot application provides APIs to manage products for a retail application. It allows users to list active products, search for products based on various criteria, create, update, and delete products. Additionally, the application includes an approval queue mechanism for handling products that require approval before being made active.

## Features

- List Active Products: Get the list of active products sorted by the latest first.

- Search Products: Search for products based on the given criteria (product name, price range, and posted date range).

- Create a Product: Create a new product, but the price must not exceed $10,000. If the price is more than $5,000, the product will be pushed to the approval queue.

- Update a Product: Update an existing product, but if the price is more than 50% of its previous price, the product will be pushed to the approval queue.

- Delete a Product: Delete a product, and it will be pushed to the approval queue.

- Get Products in Approval Queue: Get all the products in the approval queue, sorted by request date (earliest first).

- Approve a Product: Approve a product from the approval queue. The product state will be updated, and it will no longer appear in the approval queue.

- Reject a Product: Reject a product from the approval queue. The product state will remain the same, and it will no longer appear in the approval queue.

## Getting Started

### Prerequisites

- Java 8 or above
- Maven (for building the project)
- MySQL (for database storage)

### Setup

1. Clone the repository:

```bash
git clone <repository_url>
```

2. Configure MySQL:

   - Install MySQL on your system if you haven't already.
   - Create a new database for the application (e.g., `retail_app_db`).
   - Update the database connection configuration in the `application.properties` file (`src/main/resources`) with your MySQL credentials and database name:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/retail_app_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=<YOUR_MYSQL_USERNAME>
   spring.datasource.password=<YOUR_MYSQL_PASSWORD>
   ```

   Replace `<YOUR_MYSQL_USERNAME>` and `<YOUR_MYSQL_PASSWORD>` with your MySQL credentials.

3. Build the project:

```bash
cd retail-application
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

The application will start, and the APIs will be available at `http://localhost:8080`.

## API Documentation

You can access the API documentation and test the APIs using Swagger UI.

- Swagger UI: `http://localhost:8080/swagger-ui.html`

## Usage

Use tools like Postman, cURL, or any HTTP client to interact with the APIs. You can also use the Swagger UI for testing.

For example:

1. Get the list of active products:
   ```
   GET http://localhost:8080/api/products
   ```

2. Search for products:
   ```
   GET http://localhost:8080/api/products/search?productName=ProductA&minPrice=100&maxPrice=500&minPostedDate=2023-01-01T00:00:00&maxPostedDate=2023-07-01T23:59:59
   ```

3. Create a new product:
   ```
   POST http://localhost:8080/api/products
   Body: {
       "name": "ProductB",
       "price": 800,
       "status": "ACTIVE"
   }
   ```

4. Update an existing product:
   ```
   PUT http://localhost:8080/api/products/1
   Body: {
       "name": "UpdatedProductB",
       "price": 900,
       "status": "ACTIVE"
   }
   ```

5. Delete a product:
   ```
   DELETE http://localhost:8080/api/products/1
   ```

## License

This project is licensed under the [MIT License](LICENSE).

---

Replace `<repository_url>` with the actual URL of your Git repository.
