package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import com.google.gson.annotations.SerializedName

data class WinsWithFullSquad(
    @SerializedName("category")
    var category: Any?,
    @SerializedName("displayCategory")
    var displayCategory: String,
    @SerializedName("displayName")
    var displayName: String,
    @SerializedName("displayType")
    var displayType: String,
    @SerializedName("displayValue")
    var displayValue: String,
    @SerializedName("metadata")
    var metadata: MetadataXXXXXXXXXXXXXXX,
    @SerializedName("percentile")
    var percentile: Double,
    @SerializedName("rank")
    var rank: Any?,
    @SerializedName("value")
    var value: Double
)