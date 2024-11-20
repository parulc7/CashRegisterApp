
/**
 * The CashRegister class holds the logic for performing transactions.
 *
 * @param change The change that the CashRegister is holding.
 */
class CashRegister(private val change: Change) {
    /**
     * Performs a transaction for a product/products with a certain price and a given amount.
     *
     * @param price The price of the product(s).
     * @param amountPaid The amount paid by the shopper.
     *
     * @return The change for the transaction.
     *
     * @throws TransactionException If the transaction cannot be performed.
     */
    fun performTransaction(price: Long, amountPaid: Long): Change {
        if(price == amountPaid) {
            return Change.none()
        }
        val returnedChange = Change()
        var amountToBeReturned = amountPaid - price
        if(amountToBeReturned < 0) {
            throw TransactionException("Amount Paid is less than the Price. Please pay more money.")
        }
        // Check if we can return everything using bills
        for (changeItem in Bill.values()) {
            var count = 0
            while(canReturnInMonetoryItem(changeItem, amountToBeReturned, "BILL")) {
                count++
                this.change.remove(changeItem, 1)
                amountToBeReturned -= (changeItem.minorValue.toLong()/100)
            }
            returnedChange.add(changeItem, count)
        }

        // If still some money is owed, try to return in coins
        if(amountToBeReturned != 0L) {
            for (changeItem in Coin.values()) {
                var count = 0
                while(canReturnInMonetoryItem(changeItem, amountToBeReturned, "COIN")) {
                    count++
                    this.change.remove(changeItem, 1)
                    amountToBeReturned -= (changeItem.minorValue.toLong()/100)
                }
                returnedChange.add(changeItem, count)
            }
        }
        return returnedChange
    }

    fun canReturnInMonetoryItem(moneyItem : MonetaryElement, amountToBeReturned : Long, type : String): Boolean {
         if(type == "BILL") {
             return moneyItem.minorValue.toLong()/100 <= amountToBeReturned && this.change.getCount(moneyItem) > 0
         }
        return moneyItem.minorValue.toLong()/100 <= amountToBeReturned && this.change.getCount(moneyItem) > 0
    }

    fun getCurrentChangeData(): Change {
        return this.change
    }

    class TransactionException(message: String, cause: Throwable? = null) : Exception(message, cause)
}
