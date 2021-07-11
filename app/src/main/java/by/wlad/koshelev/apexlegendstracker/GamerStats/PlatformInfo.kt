package by.wlad.koshelev.apexlegendstracker.GamerStats


import com.google.gson.annotations.SerializedName

data class PlatformInfo(
//    @SerializedName("additionalParameters")
//    var additionalParameters: Any?,
    @SerializedName("avatarUrl")
    var avatarUrl: String,
    @SerializedName("platformSlug")
    var platformSlug: String,
    @SerializedName("platformUserHandle")
    var platformUserHandle: String,
    @SerializedName("platformUserId")
    var platformUserId: String,
    @SerializedName("platformUserIdentifier")
    var platformUserIdentifier: String
)