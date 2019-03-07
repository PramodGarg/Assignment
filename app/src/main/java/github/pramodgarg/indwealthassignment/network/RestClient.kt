package github.pramodgarg.indwealthassignment.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface ApiInterface {


    @GET("api/v2/funds/getList")
    fun getTop(@QueryMap params: HashMap<String, String>, @HeaderMap header: HashMap<String, String>): Call<FundsResponse>
}


object RestClient {

    private val BASE_URL = "https://dev.indiawealth.in/"

    var apiInterface: ApiInterface


    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(getLoggingInterceptor()).build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}

const val INITIAL_PAGE_SIZE = 20
const val PAGE_SIZE = 16

fun getHeaderParams(): HashMap<String, String> {
    return hashMapOf("Authorization" to "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxMDI2LCJ1c2VybmFtZSI6Ijk5MTAzMzUwMzMiLCJleHAiOjE1NTE5NjI2ODIsImVtYWlsIjoiS0FTSFlBUEFTSElTSEBHTUFJTC5DT00iLCJtb2JpbGUiOiI5OTEwMzM1MDMzIn0.IEdzEeRCjglPzQWf-FYLvCoKBnBK6ao0a26Ez5OANBc")
}

