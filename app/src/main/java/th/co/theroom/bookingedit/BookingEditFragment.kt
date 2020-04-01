package th.co.theroom.bookingedit

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
import kotlinx.android.synthetic.main.fragment_booking_edit.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import th.co.theroom.R
import th.co.theroom.adapter.SpinnerAdapter
import th.co.theroom.adapter.SpinnerRoomAdapter
import th.co.theroom.alert.AlertMessageDialogFragment
import th.co.theroom.base.AppConfig
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.RoomEntity
import th.co.theroom.shareviewmodel.ShareViewModel
import java.text.SimpleDateFormat
import java.util.*

class BookingEditFragment : Fragment() {

    private val buildingArray = arrayListOf("กรุณาเลือกตึก", "A", "B", "C")
    private val bedTypeArray = arrayListOf("กรุณาเลือกเตียง", "เตียงเดี่ยว", "เตียงคู่")
    private val peopleSizeArray = arrayListOf("กรุณาเลือกจำนวนคน", "3", "4", "9")

    private var bookingEntityBeforeData = BookingEntity()
    private val bookingEntity = BookingEntity()
    private var rowIdBooking = -1
    private var buildingNumber = ""
    private var bedType = ""
    private var peopleSize = ""
    private var roomNumber = ""
    private var dateCheckIn = ""
    private var dateCheckOut = ""
    private var mDay: Int = 0
    private var mMonth: Int = 0
    private var mYear: Int = 0
    private var firstPage: Boolean = true

    private val vmShare: ShareViewModel by sharedViewModel()
    private val vm: BookingEditFragmentViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        observeData()

        btn_back.setOnClickListener {
            vmShare.changeTitleBar("ข้อมูลการจอง")
            parentFragmentManager.popBackStack()
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

            val dateCheckInSplit = dateCheckIn.split("/")
            calendar.set(
                    dateCheckInSplit[2].toInt(),
                    dateCheckInSplit[1].toInt() - 1,
                    dateCheckInSplit[0].toInt()
            )
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
            datePickerDialog.accentColor = ContextCompat.getColor(context!!, R.color.colorPrimary)

            val dateCheckOutSplit = dateCheckOut.split("/")
            calendar.set(
                    dateCheckOutSplit[2].toInt(),
                    dateCheckOutSplit[1].toInt() - 1,
                    dateCheckOutSplit[0].toInt()
            )
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
                    rowId = rowIdBooking
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
                    dateEditData = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date())
                }

                val changeRoom = bookingEntity.roomNumber != roomNumber

                vm.editBookingRoom(bookingEntity, changeRoom, bookingEntityBeforeData)
            }
        }
    }

    private fun observeData() {
        vmShare.sendDataFragmentLiveData.observe(viewLifecycleOwner, Observer { bookingEntity ->
            bookingEntityBeforeData = bookingEntity

            rowIdBooking = bookingEntity.rowId
            et_bookingName.setText(bookingEntity.bookingName)
            et_dateCheckIn.setText(bookingEntity.dateCheckIn)
            et_dateCheckOut.setText(bookingEntity.dateCheckOut)
            dateCheckIn = bookingEntity.dateCheckIn
            dateCheckOut = bookingEntity.dateCheckOut
            roomNumber = bookingEntity.roomNumber
            buildingNumber = bookingEntity.buildingNumber

            bookingEntity.bedType?.let {
                bedType = it
            }

            bookingEntity.peopleSize?.let {
                peopleSize = it
            }

            sp_buildingNumber.adapter = SpinnerAdapter(buildingArray)
            val indexBuilding = buildingArray.indexOfFirst { it == bookingEntity.buildingNumber }
            sp_buildingNumber.setSelection(indexBuilding)

            setOnItemSelected()
        })

        vm.getRoomNumberLiveData.observe(viewLifecycleOwner, Observer { roomEntity ->
            vm.sortedItem(sp_buildingNumber.selectedItem.toString(), roomNumber, roomEntity)

            vm.sortedRoomNumberLiveData.observe(viewLifecycleOwner, Observer { roomEntitySum ->
                sp_roomNumber.adapter = SpinnerRoomAdapter(roomEntitySum.toMutableList())
                if (firstPage) {
                    val indexRoom = roomEntitySum.indexOfFirst { it.roomNumber == roomNumber }
                    sp_roomNumber.setSelection(indexRoom)
                    firstPage = false
                }
            })
        })

        vm.editBookingRoomLiveData.observe(viewLifecycleOwner, Observer {
            AlertMessageDialogFragment.Builder()
                    .setMessage("แก้ไขข้อมูลเรียบร้อย")
                    .setCallback {
                        vmShare.changeTitleBar("ข้อมูลการจอง")
                        parentFragmentManager.popBackStack()
                    }
                    .build()
                    .show(parentFragmentManager, AppConfig.TAG)
        })

        vm.errorLiveData.observe(viewLifecycleOwner, Observer {
            dialogAlert(it)
        })
    }

    private fun setOnItemSelected() {
        sp_buildingNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if (position != 0) sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                else if (position == 0) {
                    sp_bedType.adapter = SpinnerAdapter(arrayListOf())
                    sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                }

                when (buildingArray[position]) {
                    "A", "B" -> {
                        tv_bedType.text = "ประเภทห้อง"
                        sp_bedType.adapter = SpinnerAdapter(bedTypeArray)
                        if (firstPage) {
                            val indexBedType = bedTypeArray.indexOfFirst { it == bedType }
                            sp_bedType.setSelection(indexBedType)
                        }
                    }
                    "C" -> {
                        tv_bedType.text = "จำนวนคน"
                        sp_bedType.adapter = SpinnerAdapter(peopleSizeArray)
                        if (firstPage) {
                            val indexBedType = peopleSizeArray.indexOfFirst { it == peopleSize }
                            sp_bedType.setSelection(indexBedType)
                        }
                    }
                }
            }
        }

        sp_bedType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if (position != 0) {
                    when (sp_buildingNumber.selectedItem.toString()) {
                        "A", "B" -> {
                            if (firstPage) {
                                vm.getRoomNumberByBedTypeAll(
                                        buildingNumber,
                                        sp_bedType.selectedItem.toString()
                                )
                            } else {
                                vm.getRoomNumberByBedTypeAll(
                                        sp_buildingNumber.selectedItem.toString(),
                                        sp_bedType.selectedItem.toString()
                                )
                            }
                        }
                        "C" -> {
                            if (firstPage) {
                                vm.getRoomNumberByPeopleSizeAll(
                                        buildingNumber,
                                        sp_bedType.selectedItem.toString()
                                )
                            } else {
                                vm.getRoomNumberByPeopleSizeAll(
                                        sp_buildingNumber.selectedItem.toString(),
                                        sp_bedType.selectedItem.toString()
                                )
                            }
                        }
                    }
                } else if (position == 0) {
                    sp_roomNumber.adapter = SpinnerRoomAdapter(arrayListOf())
                }
            }
        }
    }

    private fun dialogAlert(message: String) {
        AlertMessageDialogFragment.Builder()
                .setMessage(message)
                .build()
                .show(parentFragmentManager, AppConfig.TAG)
    }

    companion object {
        fun newInstance(): BookingEditFragment {
            return BookingEditFragment()
        }
    }
}
