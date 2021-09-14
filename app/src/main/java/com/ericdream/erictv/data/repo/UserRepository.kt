package com.ericdream.erictv.data.repo

import android.content.Context
import com.ericdream.erictv.C
import com.ericdream.erictv.data.model.UserSettingIO
import com.google.gson.Gson
import javax.inject.Inject

class UserRepository @Inject constructor(c: Context) {

    private val sharedPreferences = c.getSharedPreferences(C.APP_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    fun getUserSetting(): UserSettingIO {
        return sharedPreferences.getString(C.KEY_USER_SETTING, null)?.let {
            gson.fromJson(it, UserSettingIO::class.java)
        } ?: UserSettingIO()
    }

    fun setUserSetting(userSettingIO: UserSettingIO) {
        gson.toJson(userSettingIO).also {
            sharedPreferences.edit()
                .putString(C.KEY_USER_SETTING, it).apply()
        }
    }
}