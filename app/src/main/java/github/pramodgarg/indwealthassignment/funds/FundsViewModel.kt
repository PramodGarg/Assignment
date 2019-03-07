package github.pramodgarg.indwealthassignment.funds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import github.pramodgarg.indwealthassignment.funds.paging.FundDataFactory
import github.pramodgarg.indwealthassignment.network.Fund
import github.pramodgarg.indwealthassignment.network.INITIAL_PAGE_SIZE
import github.pramodgarg.indwealthassignment.network.PAGE_SIZE

class FundsViewModel : ViewModel() {

    val networkState: LiveData<Int>
    val fundsLiveData: LiveData<PagedList<Fund>>
    val dataFactory: FundDataFactory = FundDataFactory()


    init {
        networkState = Transformations.switchMap(dataFactory.mutableLiveData) { it.networkState }

        val pageListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .build()

        fundsLiveData = (LivePagedListBuilder(dataFactory, pageListConfig)).build()
    }
}