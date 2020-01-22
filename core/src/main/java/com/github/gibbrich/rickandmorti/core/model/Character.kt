package com.github.gibbrich.rickandmorti.core.model

import android.os.Parcel
import android.os.Parcelable

data class Character(
    val id: Int,
    val name: String,
    val firstEpisode: String,
    val photoUrl: String,
    val origin: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(firstEpisode)
        parcel.writeString(photoUrl)
        parcel.writeString(origin)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character = Character(parcel)

        override fun newArray(size: Int): Array<Character?> = arrayOfNulls(size)
    }
}