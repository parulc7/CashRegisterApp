import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail

class CashRegisterTest {



    @Test
    fun testTransaction_whenTransactionIsSuccessAndCompleteChangeIsAvailable() {
        val initialChange = Change()
        initialChange.add(Bill.TWENTY_EURO, 1)
        initialChange.add(Coin.ONE_EURO, 5)
        val cashRegister = CashRegister(initialChange)

        val change = cashRegister.performTransaction(amountPaid = 500L, price = 475L)

        // Assert that change is correct
        assertEquals(25, change.total)
        assertEquals(0, cashRegister.getCurrentChangeData().getCount(Coin.ONE_EURO))
        assertEquals(0, cashRegister.getCurrentChangeData().getCount(Bill.TWENTY_EURO))
    }

    @Test
    fun testTransaction_whenTransactionIsSuccessAndCompleteChangeIsNotAvailable() {
        val initialChange = Change()
        initialChange.add(Bill.TWENTY_EURO, 1)
        initialChange.add(Coin.ONE_EURO, 1)
        val cashRegister = CashRegister(initialChange)

        val change = cashRegister.performTransaction(amountPaid = 500L, price = 475L)

        // Assert that change is correct
        assertEquals(21, change.total)
        assertEquals(0, cashRegister.getCurrentChangeData().getCount(Coin.ONE_EURO))
        assertEquals(0, cashRegister.getCurrentChangeData().getCount(Bill.TWENTY_EURO))
    }

    @Test
    fun testTransaction_whenTransactionIsSuccessAndPriceEqualsAmountPaid() {
        val initialChange = Change()
        initialChange.add(Coin.ONE_EURO, 10) // Add 10 coins of 1 unit
        val cashRegister = CashRegister(initialChange)

        val change = cashRegister.performTransaction(amountPaid = 15L, price = 15L)

        // Assert that change is correct
        assertEquals(0, change.total)
        assertEquals(10, cashRegister.getCurrentChangeData().getCount(Coin.ONE_EURO))
    }

    @Test
    fun testTransaction_whenTransactionFailsAsAmountPaidIsLessThanPrice() {
        val initialChange = Change()
        initialChange.add(Bill.TWENTY_EURO, 1)
        initialChange.add(Coin.ONE_EURO, 1)
        val cashRegister = CashRegister(initialChange)

        val exception = assertThrows<CashRegister.TransactionException> {
            cashRegister.performTransaction(amountPaid = 400, price = 500)
        }
        assertEquals("Amount Paid is less than the Price. Please pay more money.", exception.message)
    }
}
