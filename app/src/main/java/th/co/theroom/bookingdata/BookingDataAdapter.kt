package th.co.theroom.bookingdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_item.view.*
import th.co.theroom.R
import th.co.theroom.model.BookingEntity

class BookingDataAdapter(private val callBackOpenDetail: (BookingEntity) -> Unit, private val callEditBooking: (BookingEntity) -> Unit,
                         private val callCheckIn: (BookingEntity) -> Unit, private val callCancelBooking: (BookingEntity) -> Unit) : RecyclerView.Adapter<BookingDataAdapter.ViewHolder>() {

    private var mItems: MutableList<BookingEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mItems[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(item: BookingEntity) {
            if (item.status == "เข้าพัก") {
                itemView.view_weight.visibility = View.GONE
                itemView.ln_edit.visibility = View.GONE
                itemView.ln_checkIn.visibility = View.GONE
                itemView.ln_cancel_booking.visibility = View.GONE
            }

            itemView.tv_name_last.text = "ชื่อ-นามสกุล : ${item.bookingName}"

            itemView.tv_building.text = "ตึก : ${item.buildingNumber}"

            itemView.tv_roomNumber.text = "ห้อง : ${item.roomNumber}"

            itemView.ln_item.setOnClickListener { callBackOpenDetail.invoke(item) }

            itemView.ln_edit.setOnClickListener { callEditBooking.invoke(item) }

            itemView.ln_checkIn.setOnClickListener { callCheckIn.invoke(item) }

            itemView.ln_cancel_booking.setOnClickListener { callCancelBooking.invoke(item) }
        }
    }

    fun setListData(response: MutableList<BookingEntity>) {
        mItems.clear()
        mItems.addAll(response)
        notifyDataSetChanged()
    }
}