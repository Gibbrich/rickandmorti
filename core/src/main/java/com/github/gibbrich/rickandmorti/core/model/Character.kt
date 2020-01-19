package com.github.gibbrich.rickandmorti.core.model

import android.os.Parcel
import android.os.Parcelable

data class Character(
    val id: Int,
    val name: String,
    val firstEpisode: Int,
    val photoUrl: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(firstEpisode)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character = Character(parcel)

        override fun newArray(size: Int): Array<Character?> = arrayOfNulls(size)
    }
}