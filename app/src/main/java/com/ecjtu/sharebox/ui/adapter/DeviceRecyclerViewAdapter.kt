package com.ecjtu.sharebox.ui.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ecjtu.sharebox.MainApplication
import com.ecjtu.sharebox.R
import com.ecjtu.sharebox.domain.DeviceInfo
import com.ecjtu.sharebox.network.AsyncNetwork
import com.ecjtu.sharebox.network.IRequestCallback
import com.ecjtu.sharebox.presenter.MainActivityDelegate
import java.lang.Exception
import java.net.HttpURLConnection

/**
 * Created by Ethan_Xiang on 2017/7/3.
 */
class DeviceRecyclerViewAdapter : RecyclerView.Adapter<DeviceRecyclerViewAdapter.VH>,View.OnClickListener{

    private var mDeviceList: MutableList<DeviceInfo>? = null

    constructor(list: MutableList<DeviceInfo>) : super() {
        mDeviceList = list
    }

    override fun getItemCount(): Int {
        return mDeviceList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH? {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.layout_device_item, parent, false)
        v.setOnClickListener(this)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH?, position: Int) {
        var info=mDeviceList?.get(position)

        var iconUrl="${info?.ip}:${info?.port}${info?.icon}"
        holder?.itemView?.setTag(R.id.extra_tag,position)
        Glide.with(holder?.itemView?.context).load("http://"+iconUrl).
                apply(RequestOptions().placeholder(R.mipmap.ic_launcher)).
                into(holder?.icon)
        holder?.name?.setText(info?.name)
    }

    override fun onClick(v: View?) {
        var position=v?.getTag(R.id.extra_tag) as Int
        var deviceInfo=mDeviceList?.get(position)
        AsyncNetwork().requestDeviceInfo("${deviceInfo?.ip}:${deviceInfo?.port}",object :IRequestCallback{
            override fun onSuccess(httpURLConnection: HttpURLConnection?, response: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(httpURLConnection: HttpURLConnection?, exception: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    class VH(item: View) : RecyclerView.ViewHolder(item) {
        var icon: ImageView? = null
        var name: TextView? =null
        var thumb: ImageView? =null
        var fileCount: TextView? =null
        init {
            icon=item.findViewById(R.id.icon) as ImageView
            name=item.findViewById(R.id.name) as TextView
            thumb=item.findViewById(R.id.content) as ImageView
            fileCount=item.findViewById(R.id.file_count) as TextView
        }
    }
}



