// Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrders(): Set<Customer> =
  //  customers.partition { it.orders.sumBy {if ( !it.isDelivered ) 1 else 0 } >
  //                        it.orders.sumBy {if (  it.isDelivered ) 1 else 0 }}
  //          .component1().toSet()

customers.filter {
    val (delivered, undelivered) = it.orders.partition { it.isDelivered }
    undelivered.size > delivered.size
}.toSet()
/* { val (More,Less)=
    customers.partition { it.orders.sumBy {if ( !it.isDelivered ) 1 else 0 } >
            it.orders.sumBy {if ( it.isDelivered ) 1 else 0 }}
            return Mose.toSet()
 */