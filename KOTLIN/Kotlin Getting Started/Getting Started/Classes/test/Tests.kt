import org.junit.Assert.*
import org.junit.Test

class Test {
    @Test fun testAddress() {
        val street = "ExampleStreet"
        val houseNumber = "1"
        val postalCode = 12345
        val city = "ExampleCity"

        val address = Address(street, houseNumber, postalCode, city)
        assertEquals(street, address.street)
        assertEquals(houseNumber, address.houseNumber)
        assertEquals(postalCode, address.postalCode)
        assertEquals(city, address.city)

        assertEquals("Address(street=$street, houseNumber=$houseNumber, postalCode=$postalCode, city=$city)", address.toString())
    }

    @Test fun testAddressBook() {
        val street = "ExampleStreet"
        val houseNumber = "1"
        val postalCode = 12345
        val city = "ExampleCity"

        val address = Address(street, houseNumber, postalCode, city)
        val address2 = Address(street, houseNumber, postalCode, city)
        val address3 = Address(street.toUpperCase(), houseNumber, postalCode, city)
        val addressBook = AddressBook(mutableListOf(address))

        assertTrue(addressBook.contains(address2))
        assertFalse(addressBook.contains(address3))
    }
}