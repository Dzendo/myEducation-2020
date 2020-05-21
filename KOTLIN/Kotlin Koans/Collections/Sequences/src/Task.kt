// Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
// Найти самый дорогой продукт среди всех поставляемых продуктов
// заказано заказчиком. Использовать `Order.is доставили ' флаг
fun findMostExpensiveProductBy(customer: Customer): Product? {
    return customer
            .orders
            .asSequence()     // взято из ответа
            .filter(Order::isDelivered)
            .flatMap{it.products.asSequence()}   // исправлено из ответа
            .maxBy(Product::price)
}

// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
// Подсчитайте, сколько раз был заказан продукт.
// Обратите внимание, что клиент может заказать один и тот же продукт несколько раз.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int =
customers.flatMap(Customer::orders).flatMap(Order::products).count{ it==product }


fun Customer.getOrderedProducts(): Sequence<Product> =
        orders.flatMap(Order::products).asSequence()  // сам


/*
Узнайте о последовательностях, они позволяют выполнять операции лениво, а не жадно.
Скопируйте реализацию из предыдущей задачи и измените ее таким образом,
чтобы использовались операции над последовательностями.
 */