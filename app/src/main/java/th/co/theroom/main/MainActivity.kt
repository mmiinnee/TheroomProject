package th.co.theroom.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import th.co.theroom.R
import th.co.theroom.base.AppConfig
import th.co.theroom.base.Constants
import th.co.theroom.booking.BookingFragment
import th.co.theroom.bookingdata.BookingDataFragment
import th.co.theroom.bookingdetail.BookingDetailFragment
import th.co.theroom.bookingedit.BookingEditFragment
import th.co.theroom.menu.MenuFragment
import th.co.theroom.shareviewmodel.ShareViewModel

class MainActivity : AppCompatActivity() {

    private val vm: ShareViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(MenuFragment.newInstance())

        vm.changeFragmentLiveData.observe(this, Observer {
            when (it) {
                Constants.BOOKING -> {
                    addFragment(BookingFragment.newInstance())
                }
                Constants.BOOKING_DATA -> {
                    toolbar.tv_title.text = "ข้อมูลการจอง"
                    addFragment(BookingDataFragment.newInstance())
                }
                Constants.BOOKING_DETAIL -> {
                    toolbar.tv_title.text = "รายละเอียด"
                    addFragment(BookingDetailFragment.newInstance())
                }
                Constants.BOOKING_EDIT -> {
                    toolbar.tv_title.text = "แก้ไขข้อมูลการจอง"
                    addFragment(BookingEditFragment.newInstance())
                }
            }
        })

        vm.changeTitleBarLiveData.observe(this, Observer {
            toolbar.tv_title.text = it
        })
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentView, fragment)
            .addToBackStack(AppConfig.TAG)
            .commit();
    }

    override fun onBackPressed() {
        finish()
    }
}
