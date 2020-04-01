package th.co.theroom.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.layernet.thaidatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_booking.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import th.co.theroom.R
import th.co.theroom.adapter.SpinnerAdapter
import th.co.theroom.adapter.SpinnerRoomAdapter
import th.co.theroom.alert.AlertMessageDialogFragment
import th.co.theroom.base.AppConfig
import th.co.theroom.base.Constants
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.RoomEntity
import java.text.SimpleDateFormat
import java.util.*

class BookingFragment : Fragment() {

    private val vm: BookingFragmentViewModel by viewModel()

    private val buildingArray = arrayListOf("กรุณาเลือกตึก", "A", "B", "C")
    private val bedTypeArray = arrayListOf("กรุณาเลือกเตียง", "เตียงเดี่ยว", "เตียงคู่")
    private val peopleSizeArray = arrayListOf("กรุณาเลือกจำนวนคน", "3", "4", "9")

    private val bookingEntity = BookingEntity()
    private var mDay: Int = 0
    private var mMonth: Int = 0
    private var mYear: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        sp_buildingNumber.adapter = SpinnerAdapter(buildingArray)

        sp_buildingNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                else if (position == 0) {
                    sp_bedType.adapter = SpinnerAdapter(arrayListOf())
                    sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                }

                when (buildingArray[position]) {
                    "A", "B" -> {
                        tv_bedType.text = "ประเภทห้อง"
                        sp_bedType.adapter = SpinnerAdapter(bedTypeArray)
                    }
                    "C" -> {
                        tv_bedType.text = "จำนวนคน"
                        sp_bedType.adapter = SpinnerAdapter(peopleSizeArray)
                    }
                }
            }
        }

        sp_bedType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    when (sp_buildingNumber.selectedItem.toString()) {
                        "A", "B" -> {
                            vm.getRoomNumberByBedType(sp_buildingNumber.selectedItem.toString(), sp_bedType.selectedItem.toString())
                        }
                        "C" -> {
                            vm.getRoomNumberByPeopleSize(sp_buildingNumber.selectedItem.toString(), sp_bedType.selectedItem.toString())
                        }
                    }
                } else if (position == 0) {
                    sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                }
            }
        }

        et_dateCheckIn.setOnClickListener {
            val calendar = Calendar.getInstance()
            mDay = calendar.get(Calendar.DAY_OF_MONTH)
            mMonth = calendar.get(Calendar.MONTH)
            mYear = calendar.get(Calendar.YEAR)

            val datePickerDialog =
                    DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        val dateParse = dateFormat.parse("$dayOfMonth/${(monthOfYear + 1)}/$year")
                        et_dateCheckIn.setText(dateFormat.format(dateParse))
                    }, mYear, mMonth, mDay)
            datePickerDialog.accentColor = ContextCompat.getColor(context!!, R.color.borrow)
            datePickerDialog.minDate = calendar
            datePickerDialog.show(activity?.fragmentManager, "")
        }

        et_dateCheckOut.setOnClickListener {
            val calendar = Calendar.getInstance()
            mDay = calendar.get(Calendar.DAY_OF_MONTH)
            mMonth = calendar.get(Calendar.MONTH)
            mYear = calendar.get(Calendar.YEAR)

            val datePickerDialog =
                    DatePickerDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        val dateParse = dateFormat.parse("$dayOfMonth/${(monthOfYear + 1)}/$year")
                        et_dateCheckOut.setText(dateFormat.format(dateParse))
                    }, mYear, mMonth, mDay)
            datePickerDialog.accentColor = ContextCompat.getColor(context!!, R.color.borrow)
            datePickerDialog.minDate = calendar
            datePickerDialog.show(activity?.fragmentManager, "")
        }

        btn_save.setOnClickListener {
            if (et_bookingName.text.toString().trim() == "") {
                dialogAlert("กรุณาระบุชื่อผู้จอง")
            } else if (sp_buildingNumber.selectedItem.toString().trim() == "" || sp_buildingNumber.selectedItem.toString().trim() == buildingArray[0]) {
                dialogAlert("กรุณาเลือกอาคาร")
            } else if (sp_bedType.selectedItem.toString().trim() == "" || sp_bedType.selectedItem.toString().trim() == bedTypeArray[0] || sp_bedType.selectedItem.toString().trim() == peopleSizeArray[0]) {
                dialogAlert("กรุณาเลือกประเภทห้อง หรือระบุจำนวนคน")
            } else if (sp_roomNumber.selectedItem.toString().trim() == "") {
                dialogAlert("กรุณาเลือกห้อง")
            } else if (et_dateCheckIn.text.toString().trim() == "") {
                dialogAlert("กรุณาระบุวันที่เข้าพัก")
            } else if (et_dateCheckIn.text.toString().trim() == "" || et_dateCheckOut.text.toString().trim() == "") {
                dialogAlert("กรุณาระบุวันที่ออก")
            } else {
                bookingEntity.apply {
                    bookingName = et_bookingName.text.toString()
                    buildingNumber = sp_buildingNumber.selectedItem.toString()
                    when (sp_buildingNumber.selectedItem.toString()) {
                        "A", "B" -> {
                            peopleSize = null
                            bedType = sp_bedType.selectedItem.toString()
                        }
                        "C" -> {
                            peopleSize = sp_bedType.selectedItem.toString()
                            bedType = null
                        }
                    }
                    roomNumber = (sp_roomNumber.selectedItem as RoomEntity).roomNumber
                    dateCheckIn = et_dateCheckIn.text.toString()
                    dateCheckOut = et_dateCheckOut.text.toString()
                    status = "จอง"
                }

                vm.bookingRoom(bookingEntity)
            }
        }

        btn_back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeData() {
        vm.getRoomNumberLiveData.observe(this, Observer {
            sp_roomNumber.adapter = SpinnerRoomAdapter(it)
        })

        vm.bookingRoomLiveData.observe(this, Observer {
            AlertMessageDialogFragment.Builder()
                    .setMessage("จองห้องพักเรียบร้อย")
                    .setCallback { parentFragmentManager.popBackStack() }
                    .build()
                    .show(parentFragmentManager, AppConfig.TAG)
        })

        vm.errorLiveData.observe(this, Observer {
            dialogAlert(it)
        })
    }

    private fun dialogAlert(message: String) {
        AlertMessageDialogFragment.Builder()
                .setMessage(message)
                .build()
                .show(parentFragmentManager, AppConfig.TAG)
    }

    companion object {
        fun newInstance(): BookingFragment {
            return BookingFragment()
        }
    }
}
