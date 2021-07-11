package by.wlad.koshelev.apexlegendstracker.GamerStats


import com.google.gson.annotations.SerializedName

data class MetadataX(
    @SerializedName("activeLegend")
    var activeLegend: String,
    @SerializedName("activeLegendName")
    var activeLegendName: String,
//    @SerializedName("activeLegendStats")
//    var activeLegendStats: Any?,
    @SerializedName("currentSeason")
    var currentSeason: Int
)