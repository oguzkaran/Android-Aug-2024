package org.csystem.app.basicviews.data.service

import android.content.Context
import org.csystem.app.android.basicviews.model.UserInfoModel
import org.csystem.app.basicviews.data.service.constant.USERS_FILE_PATH
import org.csystem.app.basicviews.data.service.constant.USERS_FORMAT
import org.csystem.data.exception.DataServiceException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

private const val DELIMITER = ":"


class UserService(context: Context) {
    private val mContext = context

    private fun writeUserInfo(bw: BufferedWriter, userInfoModel: UserInfoModel) {
        bw.write("${userInfoModel.username}${DELIMITER}")
        bw.write("${userInfoModel.name}${DELIMITER}")
        bw.write("${userInfoModel.email}${DELIMITER}")
        bw.write("${userInfoModel.maritalStatus}${DELIMITER}")
        bw.write("${userInfoModel.lastEducation}")
    }

    private fun loadUserInfo(br: BufferedReader, username: String): UserInfoModel {
        val str = br.readLine() ?: throw IOException()
        val info = str.split(DELIMITER)

        return UserInfoModel(
            username,
            info[1],
            info[2],
            info[3][0],
            info[4].toInt()
        )
    }

    private fun userFilterCallback(fis: FileInputStream, predicate: (UserInfoModel) -> Boolean): Boolean {
        var result = false

        try {
            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as UserInfoModel

                if (predicate(ri)) {
                    result = true
                    break
                }
            }
        } catch (_: EOFException) {

        }

        return result
    }

    private fun findUsersBy(fis: FileInputStream, predicate: (UserInfoModel) -> Boolean): List<UserInfoModel> {
        val users = ArrayList<UserInfoModel>()

        try {
            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as UserInfoModel

                if (predicate(ri))
                    users.add(ri)
            }
        } catch (_: EOFException) {

        }

        return users
    }

    fun saveUserData(userInfoModel: UserInfoModel) {
        try {
            val username = userInfoModel.username

            BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(
                        File(
                            mContext.filesDir,
                            USERS_FORMAT.format("${username}.txt")
                        )
                    ), StandardCharsets.UTF_8
                )
            )
                .use { writeUserInfo(it, userInfoModel) }
        } catch (ex: IOException) {
            throw DataServiceException("UserService.saveUserData", ex)
        }
    }

    fun findByUsername(username: String): UserInfoModel? {
        try {
            val file = File(
                mContext.filesDir,
                USERS_FORMAT.format("$username.txt")
            )

            return if (file.exists()) BufferedReader(
                InputStreamReader(
                    FileInputStream(
                        File(
                            mContext.filesDir,
                            USERS_FORMAT.format("$username.txt")
                        )
                    ), StandardCharsets.UTF_8
                )
            ).use { loadUserInfo(it, username) } else null
        } catch (_ : FileNotFoundException) {
            return null
        } catch (ex: IOException) {
            throw DataServiceException("UserService.findByUsername", ex)
        }
    }

    fun findUsers(count: Int): List<org.csystem.app.android.basicviews.model.UserModel> {
        val users = ArrayList<org.csystem.app.android.basicviews.model.UserModel>()
        var n = 0

        if (count <= 0)
            return users

        try {
            val fis = FileInputStream(
                File(
                    mContext.filesDir,
                    USERS_FILE_PATH
                )
            )

            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as UserInfoModel

                if (n++ == count)
                    break

                users.add(
                    org.csystem.app.android.basicviews.model.UserModel()
                        .apply { username = ri.username; name = ri.name
                    email = ri.email; maritalStatus = ri.maritalStatus; lastEducation = ri.lastEducation})
            }
        } catch (_ : FileNotFoundException) {

        }
        catch (_: EOFException) {

        }

        return users
    }

    fun isUserSaved(username: String): Boolean {
        try {
            return File(
                mContext.filesDir,
                USERS_FORMAT.format("$username.txt")
            ).exists()
        } catch (ex: IOException) {
            throw DataServiceException(
                "UserService.existsByUsername",
                ex
            )
        }
    }

    fun existsByUsername(username: String): Boolean {
        return try {
            FileInputStream(
                File(
                    mContext.filesDir,
                    USERS_FILE_PATH
                )
            ).use { userFilterCallback(it) { ui -> ui.username == username} }
        } catch (_ : FileNotFoundException) {
            false
        } catch (ex: IOException) {
            throw DataServiceException(
                "UserService.existsByUsername",
                ex
            )
        }
    }

    fun existsByUsernameAndPassword(username: String, password: String): Boolean {
        return try {
            FileInputStream(
                File(
                    mContext.filesDir,
                    USERS_FILE_PATH
                )
            )
                .use { userFilterCallback(it) {ui -> ui.username == username && ui.password == password} }
        } catch (_ : FileNotFoundException) {
            false
        } catch (ex: IOException) {
            throw DataServiceException(
                "UserService.existsByUsernameAndPassword",
                ex
            )
        }
    }

    fun registerUser(userInfoModel: UserInfoModel) {
        try {
            ObjectOutputStream(
                FileOutputStream(
                    File(
                        mContext.filesDir,
                        USERS_FILE_PATH
                    ), true
                )
            )
                .use { it.writeObject(userInfoModel) }
            File(
                mContext.filesDir,
                USERS_FORMAT.format("${userInfoModel.username}.txt")
            ).delete()
        } catch (ex: IOException) {
            throw DataServiceException("UserService.register", ex)
        }
    }

    fun updateUser(user: org.csystem.app.android.basicviews.model.UserModel) {
        TODO("Not yet implemented")
    }
}