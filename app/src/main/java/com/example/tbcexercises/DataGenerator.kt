package com.example.tbcexercises


fun getData(): MutableList<Address> {
    return mutableListOf(
        Address(1, R.drawable.office, "My Office", "SBI Building, street 3, Software Park"),
        Address(2, R.drawable.home, "My Home", "SBI Building, street 3, Software Park")
    )
}