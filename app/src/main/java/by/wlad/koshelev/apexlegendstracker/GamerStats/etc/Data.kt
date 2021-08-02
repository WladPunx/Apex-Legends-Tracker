package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("availableSegments")
    var availableSegments: List<AvailableSegment>,
    @SerializedName("expiryDate")
    var expiryDate: String,
    @Embedded @SerializedName("metadata")
    var metadata: MetadataX,
    @Embedded @SerializedName("platformInfo")
    var platformInfo: PlatformInfo,
    @SerializedName("segments")
    var segments: List<Segment>,
    @Embedded @SerializedName("userInfo")
    var userInfo: UserInfo
)