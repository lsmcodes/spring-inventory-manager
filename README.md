# Inventory Manager

<div style="display:inline-block">
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Java-black?style=for-the-badge&logo=OpenJDK&logoColor=white">
                <img src="https://img.shields.io/badge/Java-white?style=for-the-badge&logo=OpenJDK&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Maven-black?style=for-the-badge&logo=ApacheMaven&logoColor=white">
                <img src="https://img.shields.io/badge/Maven-white?style=for-the-badge&logo=ApacheMaven&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Spring_Boot-black?style=for-the-badge&logo=SpringBoot&logoColor=white">
                <img src="https://img.shields.io/badge/Spring_Boot-white?style=for-the-badge&logo=SpringBoot&logoColor=black" />
        </picture>
	    <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/FlyWay-black?style=for-the-badge&logo=FlyWay&logoColor=white">
                <img src="https://img.shields.io/badge/FlyWay-white?style=for-the-badge&logo=FlyWay&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/Swagger-black?style=for-the-badge&logo=Swagger&logoColor=white">
                <img src="https://img.shields.io/badge/Swagger-white?style=for-the-badge&logo=Swagger&logoColor=black" />
        </picture>
        <picture>
                <source media="(prefers-color-scheme: light)" srcset="https://img.shields.io/badge/MySQL-black?style=for-the-badge&logo=MySQL&logoColor=white">
                <img src="https://img.shields.io/badge/MySQL-white?style=for-the-badge&logo=MySQL&logoColor=black" />
        </picture>
</div>

## Overview

A simple REST API for inventory management.

## Endpoints

The API provides the following endpoints:

### Product

| URI                        | Method | Action                      | Parameters                                           | Request Body   |
| -------------------------- | ------ | --------------------------- | ---------------------------------------------------- | -------------- |
| `api/products`             | POST   | Creates a new product       | N/A                                                  | Product Schema |
| `api/products/{id}`        | GET    | Retrieves a product by id   | id                                                   | N/A            |
| `api/products/code/{code}` | GET    | Retrieves a product by code | code                                                 | N/A            |
| `api/products`             | GET    | Retrieves products          | name (optional), page, size, property, sortDirection | N/A            |
| `api/products/{id}`        | PUT    | Updates a product           | id                                                   | Product Schema |
| `api/products/{id}`        | DELETE | Deletes a product           | id                                                   | N/A            |

#### Product Schema

```json
{
  "code": "12456789",
  "name": "Highlighter",
  "price": 0.98,
  "quantity": 12
}
```
