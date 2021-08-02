package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import com.google.gson.annotations.SerializedName

data class MetadataXX(
    @SerializedName("bgImageUrl")
    var bgImageUrl: String,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("isActive")
    var isActive: Boolean,
    @SerializedName("name")
    var name: String,
    @SerializedName("tallImageUrl")
    var tallImageUrl: String
)