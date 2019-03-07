package github.pramodgarg.indwealthassignment.funds.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import github.pramodgarg.indwealthassignment.network.Fund

class FundDataFactory : DataSource.Factory<Int, Fund>() {

    lateinit var fundDataSource: FundPageKeyedDataSource
    val mutableLiveData by lazy { MutableLiveData<FundPageKeyedDataSource>() }

    override fun create(): DataSource<Int, Fund> {
        fundDataSource = dataSource()
        mutableLiveData.postValue(fundDataSource)
        return fundDataSource
    }

    private fun dataSource(): FundPageKeyedDataSource {
        return FundPageKeyedDataSource()
    }
}