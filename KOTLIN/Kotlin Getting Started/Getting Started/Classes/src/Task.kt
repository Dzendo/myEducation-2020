data class Address(val street: String,
                   val houseNumber: String,
                   val postalCode: Int,
                   val city: String)

//class AddressBook (val addresses: MutableList<Address> ){
//    fun contains(address: Address) = addresses.contains(address)
//}
/*
data class Address(val street: String, val houseNumber: String, val postalCode: Int, val city: String)
*/
data class AddressBook(val addresses: MutableList<Address>) {
    fun contains(address: Address) = addresses.contains(address)
}

