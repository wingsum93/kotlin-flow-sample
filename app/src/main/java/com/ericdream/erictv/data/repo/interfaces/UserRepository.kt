package com.ericdream.erictv.data.repo.interfaces

import com.ericdream.erictv.data.model.UserSettingIO

interface UserRepository {

    fun getUserSetting(): UserSettingIO

    fun setUserSetting(userSettingIO: UserSettingIO)
}