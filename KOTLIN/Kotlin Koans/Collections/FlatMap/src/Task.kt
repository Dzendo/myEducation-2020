// Return all products the given customer has ordered
fun Customer.getOrderedProducts(): List<Product> =
        orders.flatMap(Order::products)
        // orders.flatMap { it.products }

// Return all products that were ordered by at least one customer
fun Shop.getOrderedProducts(): Set<Product> =
        customers.flatMap(Customer::getOrderedProducts).toSet()
        //customers.flatMap { it.orders }.flatMap { it.products }.toSet()
