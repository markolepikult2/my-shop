# 1. Project Dependencies and Configuration

   •
   build.gradle: I added the spring-boot-starter-data-mongodb dependency to enable database interaction.
   •
   application.yml: I configured the service to connect to a local MongoDB instance on localhost:27017 and use a database named basketdb.

# 2. Data Layer

   •
   basket-service/src/main/java/org/example/basketservice/model/Item.java: A simple class representing a product and its quantity in the basket.
   •
   basket-service/src/main/java/org/example/basketservice/model/Basket.java: The core MongoDB document. It is annotated with @Document(collection = "baskets") and uses the userId as its @Id. It contains a list of Item objects.
   •
   basket-service/src/main/java/org/example/basketservice/repository/BasketRepository.java: A Spring Data interface extending MongoRepository<Basket, String>. It provides all the necessary CRUD (Create, Read, Update, Delete) operations for the Basket document out of the box.

# 3. Business Logic Layer

   •
   basket-service/src/main/java/org/example/basketservice/service/BasketService.java: An interface defining the contract for our business logic, including methods to getBasketItems and addItemToBasket.
   basket-service/src/main/java/org/example/basketservice/service/BasketServiceImpl.java: The implementation of the service.
   getBasketItems retrieves the basket for a user or returns an empty list if no basket exists.
   addItemToBasket contains the core logic: it finds a user's basket or creates a new one, checks if an item already exists to update its quantity, or adds the new item to the list before saving the basket back to MongoDB.

# 4. Presentation Layer
   •
   basket-service/src/main/java/org/example/basketservice/controller/BasketController.java: The web layer that exposes your API.
   It handles GET and POST requests for the /basket/items endpoint as defined in your OpenAPI specification.
   It uses the @RequestHeader("X-User-Id") annotation to extract the user's identity from the request, which it then passes to the service layer. This is a common pattern for securely identifying users in a microservices architecture.