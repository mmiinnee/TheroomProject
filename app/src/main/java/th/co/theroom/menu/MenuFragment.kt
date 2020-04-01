package th.co.theroom.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

import th.co.theroom.R
import th.co.theroom.base.Constants
import th.co.theroom.shareviewmodel.ShareViewModel

class MenuFragment : Fragment() {

    private val vmShare: ShareViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        btn_booking.setOnClickListener {
            vmShare.changeFragment(Constants.BOOKING)
        }

        btn_data_booking.setOnClickListener {
            vmShare.changeFragment(Constants.BOOKING_DATA)
        }
    }

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }
}
