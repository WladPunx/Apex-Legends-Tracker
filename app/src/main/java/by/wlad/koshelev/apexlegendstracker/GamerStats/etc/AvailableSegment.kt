package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import com.google.gson.annotations.SerializedName

data class AvailableSegment(
    @SerializedName("attributes")
    var attributes: Attributes,
    @SerializedName("metadata")
    var metadata: Metadata,
    @SerializedName("type")
    var type: String
)