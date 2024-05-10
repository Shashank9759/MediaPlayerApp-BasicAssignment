package com.studies.basicassigment.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.studies.basicassigment.Models.videomodel
import com.studies.basicassigment.R
import com.studies.basicassigment.Activities.VideoActivity

class videoAdapter(val context:Context,var list:List<videomodel>):RecyclerView.Adapter<videoAdapter.myviewholder>() {


    class myviewholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val videoview=itemview.findViewById<ImageView>(R.id.videoview)
        val videoDuration=itemview.findViewById<TextView>(R.id.videoDuration)
        val videoPB=itemview.findViewById<ProgressBar>(R.id.videoPB)
        val profile_image=itemview.findViewById<ImageView>(R.id.profile_image)
        val Title=itemview.findViewById<TextView>(R.id.Title)
        val ChannelName=itemview.findViewById<TextView>(R.id.ChannelName)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
       val view= LayoutInflater.from(context).inflate(R.layout.itemview,parent,false)
        return myviewholder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val currentitem= list.get(position)
        holder.videoPB.visibility=View.VISIBLE;
        Glide.with(context)
            .load(currentitem.thumbnail)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.videoPB.visibility=View.GONE
                    return false
                }

            })
            .into(holder.videoview)
        Glide.with(context).load(currentitem.channelogo).into(holder.profile_image)
        holder.videoDuration.text=currentitem.videoduration
        holder.Title.text=currentitem.title
        holder.ChannelName.text=currentitem.channelname

        holder.videoview.setOnClickListener {

            val intent=Intent(context, VideoActivity::class.java)
            intent.putExtra("videomodel",currentitem)
            context.startActivity(intent)


        }
    }

    fun ondatachange(newlist: List<videomodel>){
        this.list=newlist
        notifyDataSetChanged()
    }
}