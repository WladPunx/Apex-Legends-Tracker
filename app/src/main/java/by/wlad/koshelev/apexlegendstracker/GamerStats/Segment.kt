package by.wlad.koshelev.apexlegendstracker.GamerStats


import com.google.gson.annotations.SerializedName

data class Segment(
    @SerializedName("attributes")
    var attributes: AttributesX,
    @SerializedName("expiryDate")
    var expiryDate: String,
    @SerializedName("metadata")
    var metadata: MetadataXX,
    @SerializedName("stats")
    var stats: Stats,
    @SerializedName("type")
    var type: String
)