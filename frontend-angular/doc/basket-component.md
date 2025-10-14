# BasketComponent
- **Functionality:** This component is responsible for displaying the full details of the user's shopping basket. It implements the Frontend Aggregation pattern we discussed:
   1. It first makes an HTTP request to the basket-service (GET /basket/items) to get the list of product IDs and quantities.
   2. It then makes a second HTTP request to the catalog-service (GET /products?ids=...) to fetch the names and prices for those specific products.
   3. Finally, it merges these two data sources into a single, detailed list for display and calculates the total price of the basket.

* **User Identity:** For development purposes, the component sends a hardcoded X-User-Id: test-user header, which allows it to fetch the sample basket data you created earlier.

* **Template:** The component's HTML (basket.component.html) renders the basket contents in a clean, easy-to-read table, with a "Grand Total" at the bottom. It also handles loading and error states gracefully.