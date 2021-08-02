package by.wlad.koshelev.apexlegendstracker.GamerStats

import androidx.room.TypeConverter
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.AvailableSegment
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.Segment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConverterForGamerStatsRoom {

    private val gson = Gson()

    @TypeConverter
    fun toJson_availableSegments(segments: List<AvailableSegment?>?): String {
        return gson.toJson(segments)
    }

    @TypeConverter
    fun formJson_availableSegments(json: String?): List<AvailableSegment> {
        return gson.fromJson<List<AvailableSegment>>(
            json,
            object : TypeToken<List<AvailableSegment?>?>() {}.type
        )
    }


    @TypeConverter
    fun toJson_segments(segments: List<Segment?>?): String {
        return gson.toJson(segments)
    }

    @TypeConverter
    fun formJson_segments(json: String?): List<Segment> {
        return gson.fromJson<List<Segment>>(
            json,
            object : TypeToken<List<Segment?>?>() {}.type
        )
    }
}