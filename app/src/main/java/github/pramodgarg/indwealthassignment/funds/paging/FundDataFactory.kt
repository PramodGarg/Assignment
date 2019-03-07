package github.pramodgarg.indwealthassignment.funds.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import github.pramodgarg.indwealthassignment.network.Fund

class FundDataFactory(val fundDataSource: FundPageKeyedDataSource) : DataSource.Factory<Int, Fund>() {

    val mutableLiveData by lazy { MutableLiveData<FundPageKeyedDataSource>() }

    override fun create(): DataSource<Int, Fund> {
        mutableLiveData.postValue(fundDataSource)
        return fundDataSource
    }
}