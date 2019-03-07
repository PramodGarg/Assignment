package github.pramodgarg.indwealthassignment.funds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import github.pramodgarg.indwealthassignment.funds.paging.FundDataFactory
import github.pramodgarg.indwealthassignment.funds.paging.FundPageKeyedDataSource
import github.pramodgarg.indwealthassignment.network.Fund

class FundsViewModel : ViewModel() {

    val networkState: LiveData<Int>
    val fundLiveData: LiveData<PagedList<Fund>>
    val dataFactory: FundDataFactory = FundDataFactory(FundPageKeyedDataSource())


    init {
        networkState = Transformations.switchMap(dataFactory.mutableLiveData) { it.networkState }

        val pageListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()

        fundLiveData = (LivePagedListBuilder(dataFactory, pageListConfig)).build()
    }
}