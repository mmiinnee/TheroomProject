package th.co.theroom.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import th.co.theroom.R
import th.co.theroom.alert.AlertMessageDialogFragment
import th.co.theroom.base.AppConfig
import th.co.theroom.base.Constants
import th.co.theroom.main.MainActivity
import th.co.theroom.utils.Preference

class SplashScreenActivity : AppCompatActivity() {

    private val vm: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        init()
    }

    private fun init() {
        val firstTime = Preference.instance.getBoolean(this, Constants.FIRST_TIME, true)

        if (firstTime) {
            observeData()
            val jsonRoom = resources.assets.open("room.json").bufferedReader().use { it.readText() }
            vm.insertRoom(jsonRoom)
        } else {
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
        }
    }

    private fun observeData() {
        vm.insertRoomLiveData.observe(this, Observer {
            if (it) {
                Preference.instance.putBoolean(this, Constants.FIRST_TIME, false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                AlertMessageDialogFragment.Builder()
                        .setMessage("เกิดข้อผิดพลาดของระบบ")
                        .setCallback { finish() }
                        .build()
                        .show(supportFragmentManager, AppConfig.TAG)
            }
        })

        vm.loadingLiveData.observe(this, Observer {
            if (it) {
                progress_bar.visibility = View.VISIBLE
            } else {
                progress_bar.visibility = View.GONE
            }
        })

        vm.errorLiveData.observe(this, Observer {
            AlertMessageDialogFragment.Builder()
                    .setMessage(it)
                    .build()
                    .show(supportFragmentManager, AppConfig.TAG)
        })
    }
}
