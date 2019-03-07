package github.pramodgarg.indwealthassignment.funds.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import github.pramodgarg.indwealthassignment.funds.adapter.FundsAdapter
import github.pramodgarg.indwealthassignment.network.Fund
import github.pramodgarg.indwealthassignment.network.FundsResponse
import github.pramodgarg.indwealthassignment.network.RestClient
import github.pramodgarg.indwealthassignment.network.getHeaderParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FundPageKeyedDataSource : PageKeyedDataSource<Int, Fund>() {


    val networkState by lazy { MutableLiveData<Int>() }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Fund>) {
        requestNewsData(0, params.requestedLoadSize) { data, after, before ->
            callback.onResult(data, before, after)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Fund>) {
        requestNewsData(params.key, params.requestedLoadSize) { data, after, _ -> callback.onResult(data, after) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Fund>) {
    }

    private fun requestNewsData(key: Int, requestedLoadSize: Int, result: (List<Fund>, Int, Int) -> Unit) {
        Log.d("FundPageKeyedDataSource", "offset $key : limit $requestedLoadSize")
        networkState.postValue(FundsAdapter.LOADING)
        val body = hashMapOf("offset" to key.toString(), "limit" to requestedLoadSize.toString())

        RestClient.apiInterface.getTop(body, getHeaderParams()).enqueue(object : Callback<FundsResponse> {
            override fun onFailure(call: Call<FundsResponse>, t: Throwable) {
                Log.e("", t.localizedMessage)
               // networkState.postValue(FundsAdapter.LOADED)
            }

            override fun onResponse(call: Call<FundsResponse>, response: Response<FundsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    result(response.body()!!.data, (key + requestedLoadSize), 0)
                }
                networkState.postValue(FundsAdapter.LOADED)
            }
        })
    }

}