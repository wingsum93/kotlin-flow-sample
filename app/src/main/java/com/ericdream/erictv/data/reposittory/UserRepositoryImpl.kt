package com.ericdream.erictv.data.reposittory

import android.content.Context
import com.ericdream.erictv.data.model.UserSettingObject
import com.ericdream.erictv.data.reposittory.interfaces.UserRepository
import com.ericdream.erictv.utils.Constant
import com.google.gson.Gson
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(c: Context) : UserRepository {

    private val sharedPreferences = c.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    override fun getUserSetting(): UserSettingObject {
        return sharedPreferences.getString(Constant.KEY_USER_SETTING, null)?.let {
            gson.fromJson(it, UserSettingObject::class.java)
        } ?: UserSettingObject()
    }

    override fun setUserSetting(userSettingObject: UserSettingObject) {
        gson.toJson(userSettingObject).also {
            sharedPreferences.edit()
                .putString(Constant.KEY_USER_SETTING, it).apply()
        }
    }
}