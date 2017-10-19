package br.com.pan.desafio.victorcs.top100games.logic.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by Victor Santiago on 17/10/2017.
 */

data class TwitchTopGamesData(

        @SerializedName("_total") val total: Int = 0, //1017
        @SerializedName("top") val top: List<Top> = listOf()

) : Parcelable {

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TwitchTopGamesData> = object : Parcelable.Creator<TwitchTopGamesData> {
            override fun createFromParcel(source: Parcel): TwitchTopGamesData = TwitchTopGamesData(source)
            override fun newArray(size: Int): Array<TwitchTopGamesData?> = arrayOfNulls(size)
        }
    }
}

data class Top(

        @SerializedName("game") val game: Game = Game(),
        @SerializedName("viewers") val viewers: Int = 0, //74597
        @SerializedName("channels") val channels: Int = 0 //1221

) : Parcelable {

    constructor(source: Parcel) : this(

            source.readParcelable<Game>(Game::class.java.classLoader),
            source.readInt(),
            source.readInt()

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        writeParcelable(game, 0)
        writeInt(viewers)
        writeInt(channels)

    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Top> = object : Parcelable.Creator<Top> {

            override fun createFromParcel(source: Parcel): Top = Top(source)
            override fun newArray(size: Int): Array<Top?> = arrayOfNulls(size)

        }

    }

}

data class Game(

        @SerializedName("name") val name: String = "", //League of Legends
        @SerializedName("popularity") val popularity: Int = 0, //74054
        @SerializedName("_id") val id: Int = 0, //21779
        @SerializedName("giantbomb_id") val giantbombId: Int = 0, //24024
        @SerializedName("box") val box: Box = Box(),
        @SerializedName("logo") val logo: Logo = Logo(),
        @SerializedName("localized_name") val localizedName: String = "", //League of Legends
        @SerializedName("locale") val locale: String = "" //pt-br

) : Parcelable {

    constructor(source: Parcel) : this(

            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readParcelable<Box>(Box::class.java.classLoader),
            source.readParcelable<Logo>(Logo::class.java.classLoader),
            source.readString(),
            source.readString()

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        writeString(name)
        writeInt(popularity)
        writeInt(id)
        writeInt(giantbombId)
        writeParcelable(box, 0)
        writeParcelable(logo, 0)
        writeString(localizedName)
        writeString(locale)

    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Game> = object : Parcelable.Creator<Game> {

            override fun createFromParcel(source: Parcel): Game = Game(source)
            override fun newArray(size: Int): Array<Game?> = arrayOfNulls(size)

        }

    }

}

data class Box(

        @SerializedName("large") val large: String = "", //https://static-cdn.jtvnw.net/ttv-boxart/League%20of%20Legends-272x380.jpg
        @SerializedName("medium") val medium: String = "", //https://static-cdn.jtvnw.net/ttv-boxart/League%20of%20Legends-136x190.jpg
        @SerializedName("small") val small: String = "", //https://static-cdn.jtvnw.net/ttv-boxart/League%20of%20Legends-52x72.jpg
        @SerializedName("template") val template: String = "" //https://static-cdn.jtvnw.net/ttv-boxart/League%20of%20Legends-{width}x{height}.jpg

) : Parcelable {

    constructor(source: Parcel) : this(

            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        writeString(large)
        writeString(medium)
        writeString(small)
        writeString(template)

    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Box> = object : Parcelable.Creator<Box> {

            override fun createFromParcel(source: Parcel): Box = Box(source)
            override fun newArray(size: Int): Array<Box?> = arrayOfNulls(size)

        }

    }

}

data class Logo(

        @SerializedName("large") val large: String = "", //https://static-cdn.jtvnw.net/ttv-logoart/League%20of%20Legends-240x144.jpg
        @SerializedName("medium") val medium: String = "", //https://static-cdn.jtvnw.net/ttv-logoart/League%20of%20Legends-120x72.jpg
        @SerializedName("small") val small: String = "", //https://static-cdn.jtvnw.net/ttv-logoart/League%20of%20Legends-60x36.jpg
        @SerializedName("template") val template: String = "" //https://static-cdn.jtvnw.net/ttv-logoart/League%20of%20Legends-{width}x{height}.jpg

) : Parcelable {

    constructor(source: Parcel) : this(

            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {

        writeString(large)
        writeString(medium)
        writeString(small)
        writeString(template)

    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Logo> = object : Parcelable.Creator<Logo> {

            override fun createFromParcel(source: Parcel): Logo = Logo(source)
            override fun newArray(size: Int): Array<Logo?> = arrayOfNulls(size)

        }

    }

}