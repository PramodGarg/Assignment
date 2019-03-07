package github.pramodgarg.indwealthassignment.funds

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.pramodgarg.indwealthassignment.R
import github.pramodgarg.indwealthassignment.funds.adapter.FundsAdapter
import github.pramodgarg.indwealthassignment.network.Fund
import kotlinx.android.synthetic.main.fragment_funds.*

class FundsFragment : Fragment() {

    companion object {
        const val TAG = "FundsFragment"
        fun newInstance() = FundsFragment()
    }

    val adapter by lazy { FundsAdapter() }

    val viewModel: FundsViewModel by lazy { ViewModelProviders.of(this).get(FundsViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.fundLiveData.observe(this, Observer<PagedList<Fund>> { list -> list?.let { adapter.submitList(it) } })
        viewModel.networkState.observe(this, Observer<Int> { state ->

            if (state == FundsAdapter.LOADED && swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
            state?.let { adapter.setNetworkStatus(it) }
        })

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            viewModel.dataFactory.fundDataSource.invalidate()
        }

    }

    private fun setupRecyclerView() {
        rvItems.layoutManager = LinearLayoutManager(context)
        rvItems.adapter = adapter
    }
}