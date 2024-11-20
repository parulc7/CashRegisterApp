fun main() {
    println("Welcome to the Cash Register application!")

    // Initial change in the cash register
    val initialChange = Change()
    // Hardcoded for now
    initialChange.add(Coin.ONE_EURO, 5)
    initialChange.add(Bill.TWENTY_EURO, 3)

    val cashRegister = CashRegister(initialChange)

    try {
        var changeReturned = cashRegister.performTransaction(amountPaid = 500L, price = 475L)
        println("Change returned: $changeReturned")
        changeReturned = cashRegister.performTransaction(amountPaid = 500L, price = 475L)
        println("Change returned: $changeReturned")
    } catch (e: CashRegister.TransactionException) {
        println("Transaction failed: ${e.message}")
    }
}