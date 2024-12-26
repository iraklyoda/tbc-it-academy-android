package com.example.baseproject.models

object AddressData {
    private val addressList: MutableList<Address> = mutableListOf(
        Address(id = 1, addressName = "TBC", address = "Pekini, Street 4, Soft Park", addressType = AddressType.WORK),
        Address(id = 2, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(id = 3, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.WORK),
        Address(id = 4, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(id = 5, addressName = "TBC", address = "Pekini, Street 4, Soft Park", addressType = AddressType.WORK),
        Address(id = 6, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(id = 7, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.WORK),
        Address(id = 8, addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
    )

    fun getAddresses(): MutableList<Address> {
        return addressList
    }
}