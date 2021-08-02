package by.wlad.koshelev.apexlegendstracker.GamerStats.etc


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("arKills")
    var arKills: ArKills,
    @SerializedName("docDroneHealing")
    var docDroneHealing: DocDroneHealing,
    @SerializedName("encoreExecutionsEscaped")
    var encoreExecutionsEscaped: EncoreExecutionsEscaped,
    @SerializedName("finishers")
    var finishers: Finishers,
    @SerializedName("kills")
    var kills: Kills,
    @SerializedName("level")
    var level: Level,
    @SerializedName("phaseWalkTime")
    var phaseWalkTime: PhaseWalkTime,
    @SerializedName("rankScore")
    var rankScore: RankScore,
    @SerializedName("revives")
    var revives: Revives,
    @SerializedName("season6Kills")
    var season6Kills: Season6Kills,
    @SerializedName("season6Wins")
    var season6Wins: Season6Wins,
    @SerializedName("timesPlacedtop3")
    var timesPlacedtop3: TimesPlacedtop3,
    @SerializedName("winsWithFullSquad")
    var winsWithFullSquad: WinsWithFullSquad
)