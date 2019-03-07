package github.pramodgarg.indwealthassignment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.pramodgarg.indwealthassignment.funds.FundsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openFundsFragment()
        }
    }

    private fun openFundsFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FundsFragment.newInstance(), FundsFragment.TAG)
            .commit()
    }

}