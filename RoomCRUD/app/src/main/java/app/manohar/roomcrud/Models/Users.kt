package app.manohar.roomcrud.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)

data class Users(

    @PrimaryKey(
        autoGenerate = true
    )val id : Int = 0,
    val studentNumber: String,
    val exerciseCode: String,
    val resultObtained: String,
    val mark: String
)
