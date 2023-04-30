package com.bucket.letsbucket.data

import android.os.Parcel
import android.os.Parcelable

data class DetailData(
    val from: Int,
    val id: Long,
    val text: String?,
    val done: Boolean,
    val lifetype: Int?,
    val doneDate: String?,
    val targetDate: String?,
    var uri: String?,
    val idx: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(from)
        parcel.writeLong(id)
        parcel.writeString(text)
        parcel.writeByte(if (done) 1 else 0)
        parcel.writeValue(lifetype)
        parcel.writeString(doneDate)
        parcel.writeString(targetDate)
        parcel.writeString(uri)
        parcel.writeInt(idx)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailData> {
        override fun createFromParcel(parcel: Parcel): DetailData {
            return DetailData(parcel)
        }

        override fun newArray(size: Int): Array<DetailData?> {
            return arrayOfNulls(size)
        }
    }
}