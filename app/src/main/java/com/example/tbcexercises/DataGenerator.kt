package com.example.tbcexercises

val PENDING = OrderStatus(1, "PENDING", R.color.orange)
val DELIVERED = OrderStatus(2, "DELIVERED", R.color.green)
val CANCELLED = OrderStatus(3, "CANCELLED", R.color.red)


fun generateOrderData(): MutableList<Order> {
    return mutableListOf(
        Order(
            orderNumber = 1233, quantity = 3, subTotal = 130, status = PENDING
        ),
        Order(
            orderNumber = 1234, quantity = 2, subTotal = 150, status = PENDING
        ),
        Order(
            orderNumber = 1233, quantity = 5, subTotal = 870, status = PENDING
        ),

        Order(
            orderNumber = 233, quantity = 1, subTotal = 100, status = PENDING
        ),

        Order(
            orderNumber = 112, quantity = 3, subTotal = 500, status = DELIVERED
        ),

        Order(
            orderNumber = 113, quantity = 3, subTotal = 500, status = CANCELLED
        )


    )


}

fun generateOrderStatusData(): MutableList<OrderStatus> {
    return mutableListOf(
        OrderStatus(id = 1, name = "Pending", color = null, isSelected = true),
        OrderStatus(id = 2, name = "Delivered", color = null),
        OrderStatus(id = 3, name = "Cancelled", color = null),
    )
}