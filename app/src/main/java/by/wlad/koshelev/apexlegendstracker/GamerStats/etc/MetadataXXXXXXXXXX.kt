package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class MetadataXXXXXXXXXX(
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var iconImg: ByteArray,
    @SerializedName("iconUrl")
    var iconUrl: String,
    @SerializedName("rankName")
    var rankName: String
)