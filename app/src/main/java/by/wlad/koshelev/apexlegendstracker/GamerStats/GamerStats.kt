package by.wlad.koshelev.apexlegendstracker.GamerStats


import android.app.Application
import androidx.room.*
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.Data
import by.wlad.koshelev.apexlegendstracker.UI.ImageConvertor
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "gamer_stats_table")
data class GamerStats(
    @SerializedName("data")
    @Embedded var `data`: Data,
    var dateInfo: String = "",
    @PrimaryKey var primKey: String = ""
) {
    fun inz() {
        this.dateInfo = Date().time.toString()
        this.primKey = "${this.data.platformInfo.platformUserId}_${this.data.platformInfo.platformSlug}"


        // аватар
        this.data.platformInfo.avatarImg = ImageConvertor.urlToByte(this.data.platformInfo.avatarUrl)


        // ранг

        this.data.segments[0].stats.rankScore.metadata.iconImg =
            ImageConvertor.urlToByte(this.data.segments[0].stats.rankScore.metadata.iconUrl)


    }
}


@Dao
interface StatsDAO {
    @Insert
    suspend fun addNew(a: GamerStats)

    @Update
    suspend fun update(a: GamerStats)

    @Delete
    suspend fun deleteOne(a: GamerStats)

    @Query("delete from gamer_stats_table")
    suspend fun dellAll()

    @Query("select * from gamer_stats_table")
    suspend fun getAllGamers(): MutableList<GamerStats>

    @Query("select * from gamer_stats_table where primKey like :id")
    suspend fun getGamer(id: String): GamerStats

}

@Database(entities = arrayOf(GamerStats::class), version = 1)
@TypeConverters(ConverterForGamerStatsRoom::class)
abstract class GamerStatsDataBase : RoomDatabase() {
    abstract fun getStatsDAO(): StatsDAO

    companion object {
        private var bd: GamerStatsDataBase? = null
        fun create(apl: Application): GamerStatsDataBase {
            if (bd == null) {
                bd = Room.databaseBuilder(
                    apl,
                    GamerStatsDataBase::class.java,
                    "gamer_stats_bd_3"
                )
                    .build()
            }
            return bd!!
        }
    }
}