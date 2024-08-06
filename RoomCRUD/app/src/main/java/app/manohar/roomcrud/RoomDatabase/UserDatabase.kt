package app.manohar.roomcrud.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import app.manohar.roomcrud.Models.Users


@Database(entities = [Users::class], version = 5)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getDatabaseInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                )
//                    .addMigrations(MIGRATION_DB)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_DB = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration logic, e.g., adding a new column
//                database.execSQL("ALTER TABLE users ADD COLUMN resultObtained TEXT NOT NULL DEFAULT ''")
//                database.execSQL("ALTER TABLE users ADD COLUMN mark TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE users DROP COLUMN name")
                database.execSQL("ALTER TABLE users DROP COLUMN age")
            }
        }
    }
}
