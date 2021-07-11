package by.wlad.koshelev.apexlegendstracker.GamerStats


import com.google.gson.annotations.SerializedName

data class Season6Wins(
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
    var metadata: MetadataXXXXXXXXXXXXX,
    @SerializedName("percentile")
    var percentile: Double,
    @SerializedName("rank")
    var rank: Any?,
    @SerializedName("value")
    var value: Double
)