package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import com.google.gson.annotations.SerializedName

data class UserInfo(
//    @SerializedName("countryCode")
//    var countryCode: Any?,
//    @SerializedName("customAvatarUrl")
//    var customAvatarUrl: Any?,
//    @SerializedName("customHeroUrl")
//    var customHeroUrl: Any?,
    @SerializedName("isInfluencer")
    var isInfluencer: Boolean,
    @SerializedName("isPartner")
    var isPartner: Boolean,
    @SerializedName("isPremium")
    var isPremium: Boolean,
//    @SerializedName("isSuspicious")
//    var isSuspicious: Any?,
    @SerializedName("isVerified")
    var isVerified: Boolean,
    @SerializedName("pageviews")
    var pageviews: Int,
//    @SerializedName("socialAccounts")
//    var socialAccounts: List<Any>,
//    @SerializedName("userId")
//    var userId: Any?
)