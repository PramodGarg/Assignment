package github.pramodgarg.indwealthassignment.funds.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import github.pramodgarg.indwealthassignment.R
import github.pramodgarg.indwealthassignment.network.Fund
import github.pramodgarg.indwealthassignment.utils.gone
import github.pramodgarg.indwealthassignment.utils.inflate
import github.pramodgarg.indwealthassignment.utils.visible
import kotlinx.android.synthetic.main.item_fund.view.*

class FundsAdapter : PagedListAdapter<Fund, RecyclerView.ViewHolder>(FUNDS_DIFF_UTILS) {

    private var networkState = LOADED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_FUND) FundsVH(parent.inflate(R.layout.item_fund))
        else LoadingVH(parent.inflate(R.layout.item_loader))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {
        val pos = holder.adapterPosition

        if (holder is FundsVH && pos != -1) {
            getAdapterItem(pos).let { holder.bind(it) }
        }
    }


    class FundsVH(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(fund: Fund) {
            with(view) {
                tvName.text = fund.name

                if (fund.rating != null) {
                    tvRating.text = fund.rating.toString()
                    tvRating.visible()
                } else {
                    tvRating.gone()
                }

                tvFundType.text = fund.fundType
                tvRisk.text = fund.risk
                tvPlanType.text = fund.planType

                fund.returns?.oneYear?.let {
                    tv1Year.text = it.toString()
                    tv1Year.visible()
                } ?: tv1Year.gone()

                tvAum.text = context.getString(R.string.amount_in_cr, fund.aum)
                tvExpenseRatios.text = fund.expenseRatio.toString()

            }
        }
    }

    class LoadingVH(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return if (isLoading() && isLoadingItem(position)) VIEW_TYPE_LOADER else VIEW_TYPE_FUND
    }

    private fun isLoading() = networkState == LOADING

    fun setNetworkStatus(state: Int) {

        if (state == networkState) return
        networkState = state
        if (isLoading()) notifyItemInserted(itemCount) else notifyItemRemoved(itemCount)
    }

    private fun getAdapterItem(pos: Int): Fund {
        return if (isLoading() && isLoadingItem(pos)) getItem(pos - 1)!! else getItem(pos)!!
    }

    private fun isLoadingItem(pos: Int) = pos == itemCount - 1

    companion object {
        val FUNDS_DIFF_UTILS = object : DiffUtil.ItemCallback<Fund>() {
            override fun areItemsTheSame(oldItem: Fund?, newItem: Fund?) = oldItem === newItem

            override fun areContentsTheSame(oldItem: Fund, newItem: Fund) = oldItem == newItem

        }

        const val VIEW_TYPE_LOADER = 0
        const val VIEW_TYPE_FUND = 2

        const val LOADING = 1
        const val LOADED = 2

    }

}