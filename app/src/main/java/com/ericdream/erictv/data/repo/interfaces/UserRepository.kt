package com.ericdream.erictv.data.repo.interfaces

import com.ericdream.erictv.data.model.UserSettingObject

interface UserRepository {

    fun getUserSetting(): UserSettingObject

    fun setUserSetting(userSettingObject: UserSettingObject)
}