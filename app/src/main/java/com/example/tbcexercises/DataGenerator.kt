package com.example.tbcexercises


fun getCategories(): List<Category> {
    return listOf(
        Category(1, null, "All"),
        Category(2, R.drawable.party, "Party"),
        Category(3, R.drawable.mountain, "Camping"),
        Category(4, null, "Category1"),
        Category(5, null, "Category2"),
        Category(6, null, "Category3"),
    )
}

fun getPhotos(): List<Photo> {
    return listOf(
        Photo(
            id = 1, image = R.drawable.img1, categoryType = "Party", price = 123, title = "opaaaa"
        ),
        Photo(
            id = 2, image = R.drawable.img1, categoryType = "Camping", price = 2334, title = "dior"
        ),
        Photo(
            id = 3, image = R.drawable.img2, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img3, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img4, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img2, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img3, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img1, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img1, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img1, categoryType = "Camping", price = 123, title = "adidas"
        ),
    )
}