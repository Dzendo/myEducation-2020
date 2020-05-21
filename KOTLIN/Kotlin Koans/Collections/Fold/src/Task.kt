// Return the set of products that were ordered by all customers
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val allProducts = customers.flatMap { it.getOrderedProducts() }.toSet()
    return customers.fold(allProducts, { orderedByAll, customer ->
        orderedByAll.intersect(customer.getOrderedProducts())
    })
}

fun Customer.getOrderedProducts(): List<Product> =
        orders.flatMap(Order::products)

/* подсмотрел ответ - разбирать:
Fold собирает в SET наборы - пересечения ордера со всеми??
Узнайте о fold и reduce и внедрите функцию, которая возвращает набор продуктов,
 заказанных всеми клиентами, использующими fold.

Вы можете использовать клиента.получить заказанные продукты (),
 которые были определены в предыдущей задаче (скопировать ее реализацию).

список(1, 2, 3, 4)
    .    .fold (1) { часть продукта, элемент- & gt;
                элемент * часть продукта
    } = = 24
 */

// intersect Возвращает набор, содержащий все элементы,
// содержащиеся как в этом наборе, так и в указанной коллекции.