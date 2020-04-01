package th.co.theroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_item_adapter.view.*
import th.co.theroom.R
import th.co.theroom.model.RoomEntity

class SpinnerRoomAdapter(private var mItems: MutableList<RoomEntity>) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return mItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_adapter, parent, false)
        view.tv_nameItem.text = mItems[position].roomNumber
        return view
    }
}