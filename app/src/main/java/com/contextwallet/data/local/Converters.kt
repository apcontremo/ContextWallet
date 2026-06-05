package com.contextwallet.data.local

import androidx.room.TypeConverter
import com.contextwallet.data.local.entity.ReminderType

class Converters {
    
    @TypeConverter
    fun fromReminderType(value: ReminderType): String {
        return value.name
    }
    
    @TypeConverter
    fun toReminderType(value: String): ReminderType {
        return ReminderType.valueOf(value)
    }
}
