package net.mbonnin.arcanetracker.trackobot

import android.os.Environment
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.mbonnin.arcanetracker.*
import net.mbonnin.arcanetracker.helper.getPlayerClass
import net.mbonnin.arcanetracker.trackobot.model.HistoryList
import net.mbonnin.arcanetracker.trackobot.model.ResultData
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by martin on 10/25/16.
 */

class Trackobot {
    private val mService: Service
    private var mUser: User? = null
    private var pendingResultData: ArrayList<ResultData>? = null

    fun link(user: User) {
        mUser = user
        PaperDb.write(KEY_USER, user)
    }

    fun unlink() {
        mUser = null
        PaperDb.delete(KEY_USER)
    }

    init {

        mUser = PaperDb.read<User>(KEY_USER)

        if (Utils.isAppDebuggable) {
            mUser = User("bitter-void-terror-7444", "f762d37712")
            PaperDb.write(KEY_USER, mUser)
        }

        pendingResultData = PaperDb.read<ArrayList<ResultData>>(KEY_PENDING_RESULT_DATA)
        if (pendingResultData == null) {
            pendingResultData = ArrayList()
        }

        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()

                    val requestBuilder = request.newBuilder()
                    val path = request.url().pathSegments()[0]
                    val urlBuilder = request.url().newBuilder()
                    if (path.startsWith("users")) {

                    } else {
                        if (mUser != null) {
                            urlBuilder.addQueryParameter("username", mUser!!.username)
                            requestBuilder.addHeader("Authorization", Credentials.basic(mUser!!.username, mUser!!.password))
                        } else {
                            Timber.e("null trackobot user")
                        }
                    }

                    requestBuilder.url(urlBuilder.build())
                    request = requestBuilder.build()
                    chain.proceed(request)
                }.build()


        val retrofit = Retrofit.Builder()
                .baseUrl("https://trackobot.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        mService = retrofit.create(Service::class.java)
    }

    fun sendResult(resultData: ResultData) {
        if (!Utils.isNetworkConnected) {
            Timber.w("offline, sendResult later")
            pendingResultData!!.add(resultData)
            PaperDb.write(KEY_PENDING_RESULT_DATA, pendingResultData!!)
            return
        }

        if (pendingResultData!!.size > 10) {
            Utils.reportNonFatal(Exception("Emptying Track-o-bot queue"))
            pendingResultData!!.clear()
        }

        while (!pendingResultData!!.isEmpty()) {
            val pendingData = pendingResultData!!.removeAt(0)

            sendResultInternal(pendingData)

            PaperDb.write(KEY_PENDING_RESULT_DATA, pendingResultData!!)
        }

        sendResultInternal(resultData)
    }

    fun sendResultSingle(resultData: ResultData): Single<Lce<ResultData>> {
        Timber.w("sendResult")
        if (resultData.result.mode == null) {
            resultData.result.mode = "ranked"
        }

        // sanity check
        when (resultData.result.mode) {
            "ranked", "arena", "friendly" -> {}
            "solo" -> resultData.result.mode = "practice"
            "?" -> resultData.result.mode = "ranked"
        }


        return mService.postResults(resultData)
                .map<Lce<ResultData>>({ this.dataToLce(it) })
                .onErrorReturn({ Lce.error(it) })
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun sendResultInternal(resultData: ResultData) {
        sendResultSingle(resultData).subscribe({ it -> this.handleResponse(it) })
    }

    private fun handleResponse(lce: Lce<ResultData>) {
        if (lce.data != null) {
            val context = HDTApplication.context
            Toaster.show(context.getString(R.string.trackobotSuccess))
            Timber.d("trackobot upload success")
        } else if (lce.error != null) {
            val e = lce.error
            val message: String

            Timber.d("trackobot upload error")

            val context = HDTApplication.context
            if (e is retrofit2.HttpException) {
                message = context.getString(R.string.trackobotHttpError, e.code())
            } else if (e is SocketTimeoutException) {
                message = context.getString(R.string.trackobotTimeout)
            } else if (e is ConnectException) {
                message = context.getString(R.string.trackobotConnectError)
            } else if (e is MalformedJsonException) {
                // this happens if credentials are wrong. We get redirected to https://trackobot.com/sessions/new
                // and the json parser fails
                message = context.getString(R.string.trackobotError)
                // we reset the credentials in that case
                unlink()
            } else if (e is IOException) {
                message = context.getString(R.string.trackobotNetworkError)
            } else {
                message = context.getString(R.string.trackobotError)
            }

            Toaster.show(message)
            Utils.reportNonFatal(e)
        }
    }

    private fun <T> dataToLce(data: T?): Lce<T> {
        return if (data == null) {
            Lce.error(Exception("null data"))
        } else {
            Lce.data(data)
        }
    }

    fun testUser(newUser: User): Observable<Lce<HistoryList>> {
        link(newUser)

        return mService.historyList
                .map<Lce<HistoryList>>({ this.dataToLce(it) })
                .onErrorReturn({ Lce.error(it) })
                .toObservable()
                .startWith(Lce.loading())
                .observeOn(AndroidSchedulers.mainThread())
                .map { lce ->
                    if (lce.error != null) {
                        // if something goes wrong, rollback the user
                        unlink()
                    }
                    lce
                }
    }

    fun createUser(): Observable<Lce<User>> {
        return mService.createUser()
                .map<Lce<User>>({ this.dataToLce(it) })
                .onErrorReturn({ Lce.error(it) })
                .toObservable()
                .startWith(Lce.loading())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createOneTimeAuth(): Single<Url> {
        return mService.createOneTimeAuth()
    }

    fun currentUser(): User? {
        return mUser
    }

    companion object {
        private val KEY_USER = "USER"
        private val KEY_PENDING_RESULT_DATA = "PENDING_RESULT_DATA"
        private var INSTANCE: Trackobot? = null

        fun get(): Trackobot {
            if (INSTANCE == null) {
                INSTANCE = Trackobot()
            }
            return INSTANCE!!
        }

        fun findTrackobotFile(): File? {
            val dir = Environment.getExternalStorageDirectory()
            val files = dir.listFiles() ?: emptyArray()
            for (f in files) {
                if (f.isFile && f.name.contains(".track-o-bot")) {
                    return f
                }
            }

            return null
        }

        fun parseTrackobotFile(f: File): User? {
            try {
                val builder = StringBuilder()
                val stream = DataInputStream(FileInputStream(f))
                val usernameLen = stream.readInt()
                for (i in 0 until usernameLen / 2) {
                    builder.append(stream.readShort().toChar())
                }
                val username = builder.toString()
                builder.setLength(0)
                val passwordLen = stream.readInt()
                for (i in 0 until passwordLen / 2) {
                    builder.append(stream.readShort().toChar())
                }
                val password = builder.toString()
                return User(username, password)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        fun getHero(classIndex: Int): String {
            return getPlayerClass(classIndex).toLowerCase()
        }

        fun getMode(gameType: String): String {
            return when (gameType) {
                GameType.GT_ARENA.name -> "arena"
                GameType.GT_VS_AI.name -> "practice"
                GameType.GT_CASUAL.name -> "ranked"
                GameType.GT_RANKED.name -> "ranked"
                else -> "ranked"
            }
        }
    }
}

