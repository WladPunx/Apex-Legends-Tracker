package by.wlad.koshelev.apexlegendstracker.GamerStats


import androidx.appcompat.app.AppCompatActivity
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gamer_stats_table")
data class _GamerStats(
    @SerializedName("data")
    @Embedded var `data`: Data,
    @PrimaryKey(autoGenerate = true) var primLey: Int = 0
)


@Dao
interface StatsDAO {
    @Insert
    suspend fun addNew(a: _GamerStats)

    @Query("delete from gamer_stats_table")
    suspend fun dellAll()

    @Query("select * from gamer_stats_table")
    suspend fun getAll(): MutableList<_GamerStats>

}

@Database(entities = arrayOf(_GamerStats::class), version = 1)
@TypeConverters(_ConverterForGamerStatsRoom::class)
abstract class GamerStatsDataBase : RoomDatabase() {
    abstract fun getStatsDAO(): StatsDAO

    companion object {
        lateinit var bd: GamerStatsDataBase
        lateinit var dao: StatsDAO
        fun create(app: AppCompatActivity) {
            bd = Room.databaseBuilder(
                app,
                GamerStatsDataBase::class.java,
                "gamer_stats_bd"
            )
                .build()

            dao = bd.getStatsDAO()

        }
    }
}