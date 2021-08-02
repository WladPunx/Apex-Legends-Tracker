package by.wlad.koshelev.apexlegendstracker.GamerStats


import androidx.appcompat.app.AppCompatActivity
import androidx.room.*
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.Data
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
        lateinit var bd: GamerStatsDataBase
        lateinit var dao: StatsDAO
        fun create(app: AppCompatActivity) {
            bd = Room.databaseBuilder(
                app,
                GamerStatsDataBase::class.java,
                "gamer_stats_bd_1"
            )
                .build()

            dao = bd.getStatsDAO()

        }
    }
}