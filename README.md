Запуск программы: docker compose up
Ссылки для доступа к данным:
Пользователи
GET http://localhost:8080/api/users
GET http://localhost:8080/api/users/{id}
POST http://localhost:8080/api/users
Товары
GET http://localhost:8080/api/products
GET http://localhost:8080/api/products/{id}
POST http://localhost:8080/api/products
Заказы
GET http://localhost:8080/api/orders
GET http://localhost:8080/api/orders/{id}
POST http://localhost:8080/api/orders
PATCH http://localhost:8080/api/orders/{id}/status
GET http://localhost:8080/api/orders/{id}/history
Сообщения по заказу
GET http://localhost:8080/api/orders/{orderId}/messages
POST http://localhost:8080/api/orders/{orderId}/messages
Замены товаров
GET http://localhost:8080/api/orders/{orderId}/replacements
POST http://localhost:8080/api/orders/{orderId}/replacements
PATCH http://localhost:8080/api/replacements/{id}/decision