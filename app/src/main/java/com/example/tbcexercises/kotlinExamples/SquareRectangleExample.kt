package com.example.tbcexercises.kotlinExamples

//open class Rectangle(var width: Int, var height: Int) {
//    open fun setWidth(x: Int) {
//        width = x
//    }
//
//    open fun setHeight(y: Int) {
//        height = y
//    }
//}
//
//class Square(side: Int) : Rectangle(side, side) {
//
//    //function is called setWidth and we are
//    //updating height  dont make any sense
//    //bad code  Violation of LSP
//    override fun setWidth(x: Int) {
//        width = x
//        height = x
//    }
//
//    //function is called setHeight and we are
//    //updating width dont make any sense
//    //bad code Violation of LSP
//    override fun setHeight(y: Int) {
//        height = y
//        width = y
//    }
//}

fun main() {
//    val rect: Rectangle = Square(5)
//    rect.setWidth(10)
//    println(rect.width * rect.height)
}

interface Shape {
    fun area(): Int
    fun perimeter(): Int
}

class Rectangle(private var width: Int, private var height: Int) : Shape {
    fun setWidth(width: Int) {
        this.width = width
    }

    fun setHeight(height: Int) {
        this.height = height
    }

    override fun area(): Int = width * height
    override fun perimeter(): Int {
        return (width + height) + 2
    }
}

class Square(private var side: Int) : Shape {
    fun setSide(side: Int) {
        this.side = side
    }

    override fun area(): Int = side * side
    override fun perimeter(): Int {
       return side * 4
    }
}