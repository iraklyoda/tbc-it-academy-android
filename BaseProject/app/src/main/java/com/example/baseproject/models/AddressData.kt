package com.example.baseproject.models

object AddressData {
    private val addressList: MutableList<Address> = mutableListOf(
        Address(addressName = "TBC", address = "Pekini, Street 4, Soft Park", addressType = AddressType.WORK),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.WORK),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(addressName = "TBC", address = "Pekini, Street 4, Soft Park", addressType = AddressType.WORK),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.WORK),
        Address(addressName = "Home 1", address = "Pekini, Street 5, Soft Park", addressType = AddressType.HOME),
    )

    fun getAddresses(): MutableList<Address> {
        return addressList
    }

    fun addAddress(address: Address) {
        addressList.add(0, address)
    }

    fun updateAddress(position: Int, addressName: String, address: String, addressType: AddressType) {
        addressList[position].address = address
        addressList[position].addressName = addressName
        addressList[position].addressType = addressType
    }
}