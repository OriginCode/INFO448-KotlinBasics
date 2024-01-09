package edu.uw.basickotlin

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(arg: Any): String {
    return when (arg) {
        is String -> if (arg == "Hello") "world" else "Say what?"
        is Int -> when (arg) {
            0 -> "zero"
            1 -> "one"
            in 2..10 -> "low number"
            else -> "a number"
        }
        else -> "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(lhs: Int, rhs: Int): Int = lhs + rhs

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(lhs: Int, rhs: Int): Int = lhs - rhs

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(lhs: Int, rhs: Int, op: (Int, Int) -> Int) = op(lhs, rhs)

// write a class "Person" with first name, last name and age
class Person(val firstName: String, val lastName: String, val age: Int) {
    val debugString = "[Person firstName:${this.firstName} lastName:${this.lastName} age:${this.age}]"
}

// write a class "Money"
class Money(val amount: Int, val currency: String) {
    private val allowedCurrencies = listOf("USD", "EUR", "CAN", "GBP")
    init {
        if (amount < 0 || currency !in allowedCurrencies)
            throw IllegalArgumentException()
    }

    fun convert(newCurrency: String): Money {
        if (newCurrency !in allowedCurrencies)
            throw IllegalArgumentException()

        if (this.currency == newCurrency)
            return Money(this.amount, newCurrency)

        return Money(
            when (Pair(this.currency, newCurrency)) {
                Pair("USD", "EUR") -> (this.amount * 1.5).toInt()
                Pair("USD", "CAN") -> (this.amount * 1.25).toInt()
                Pair("USD", "GBP") -> (this.amount * 0.5).toInt()
                Pair("EUR", "USD") -> (this.amount * 2 / 3).toInt()
                Pair("CAN", "USD") -> (this.amount * 0.8).toInt()
                Pair("GBP", "USD") -> (this.amount * 2).toInt()
                else -> convert("USD").convert(newCurrency).amount
            }, newCurrency)
    }

    operator fun plus(other: Money): Money {
        if (this.currency != other.currency)
            return this.plus(other.convert(this.currency))

        return Money(this.amount + other.amount, this.currency)
    }
}
