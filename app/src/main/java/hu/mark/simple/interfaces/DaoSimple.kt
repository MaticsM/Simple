package hu.mark.simple.interfaces

import android.arch.persistence.room.*
import hu.mark.simple.data.SimpleCache

@Dao
interface DaoSimple {

    @Insert
    fun insertOnlySingleResponse(response: SimpleCache)

    @Insert
    fun insertMultipleResponses(responses: List<SimpleCache>)

    @Query("SELECT * FROM SimpleCache")
    fun fetchResponses(): List<SimpleCache>

    @Update
    fun updateResponse(response: SimpleCache)

    @Delete
    fun deleteResponse(response: SimpleCache)
}