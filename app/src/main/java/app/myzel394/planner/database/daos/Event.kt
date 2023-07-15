package app.myzel394.planner.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.myzel394.planner.database.objects.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {
    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun findById(id: Long): Event

    @Insert
    fun insert(event: Event): Long

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)
}
