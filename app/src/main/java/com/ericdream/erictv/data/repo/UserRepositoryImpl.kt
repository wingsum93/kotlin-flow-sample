package com.ericdream.erictv.data.repo

import android.content.Context
import com.ericdream.erictv.data.model.UserSettingIO
import com.ericdream.erictv.data.repo.interfaces.UserRepository
import com.ericdream.erictv.utils.Constant
import com.google.gson.Gson
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(c: Context) : UserRepository {

    private val sharedPreferences = c.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    override fun getUserSetting(): UserSettingIO {
        return sharedPreferences.getString(Constant.KEY_USER_SETTING, null)?.let {
            gson.fromJson(it, UserSettingIO::class.java)
        } ?: UserSettingIO()
    }

    override fun setUserSetting(userSettingIO: UserSettingIO) {
        gson.toJson(userSettingIO).also {
            sharedPreferences.edit()
                .putString(Constant.KEY_USER_SETTING, it).apply()
        }
    }
}