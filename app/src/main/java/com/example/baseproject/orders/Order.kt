package com.example.baseproject.orders

import android.os.Parcel
import android.os.Parcelable
import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val image: Int,
    val colorName: String,
    val color: Int,
    val quantity: Int,
    val price: Double,
    val status: OrderStatus
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        OrderStatus.valueOf(parcel.readString() ?: OrderStatus.ACTIVE.name)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeString(colorName)
        parcel.writeInt(color)
        parcel.writeInt(quantity)
        parcel.writeDouble(price)
        parcel.writeString(status.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}