// Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
fun findMostExpensiveProductBy(customer: Customer): Product? =
     customer.orders.filter { it.isDelivered }.flatMap(Order::products).maxBy { it.price }


// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int =
    customers.flatMap(Customer::orders).flatMap(Order::products).filter { it==product }.size

fun Customer.getOrderedProducts(): List<Product> =
        orders.flatMap(Order::products)
