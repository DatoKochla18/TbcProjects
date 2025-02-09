package com.example.tbcexercises.kotlinExamples

import java.io.FileNotFoundException
import java.io.IOException

open class Animal() {
    open fun move(meter: Int): Number {
        return meter
    }
}

class Dog : Animal() {
    override fun move(meter: Int): Number {
        return meter
    }
}


fun main() {

}

open class Player {
    var health = 100
    var isDead = false

    open fun killPlayer() { //postcondition health = 0, and isDead = true
        health = 0
        isDead = true
    }
}

class UndeadPlayer : Player() {
    override fun killPlayer() { //postcondition broken, player does not die
        health = 1
    }
}

interface Flyable {
    fun fly()
}

open class Bird {
    open fun move() {
        println("moving")
    }
}

class Eagle : Bird(), Flyable {
    override fun fly() {
        println("Eagle is flying!")
    }

    override fun move() {
        println("Eagle is moving")
    }
}

class Penguin : Bird() {
    override fun move() {
        println("Penguin is moving")
    }
}
