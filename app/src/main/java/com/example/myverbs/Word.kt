package com.example.myverbs

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @ColumnInfo(name = "text_word")
    val text: String,
    @ColumnInfo(name = "meaning_word")
    val meanig: String,
    @ColumnInfo(name = "status_word")
    val status: String): Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    var wordId: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""

    ) {
        wordId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(meanig)
        parcel.writeString(status)
        parcel.writeInt(wordId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }
}

