package com.example.testioasys2.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testioasys2.data.response.EnterpriseDataResponse
import com.example.testioasys2.databinding.ItemEnterpriseBinding
import com.example.testioasys2.utils.Constants

class EnterpriseListAdapter(
    private val enterprises: List<EnterpriseDataResponse>,
    val onItemClickListener: ((enterprise: EnterpriseDataResponse) -> Unit)
    ): RecyclerView.Adapter<EnterpriseListAdapter.EnterpriseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnterpriseViewHolder {
        val view = ItemEnterpriseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EnterpriseViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: EnterpriseViewHolder, position: Int) {
        holder.bindView(enterprises[position])
    }

    override fun getItemCount() = enterprises.count()

    class EnterpriseViewHolder(
        binding: ItemEnterpriseBinding,
        private val onItemClickListener: (enterprise: EnterpriseDataResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        private val image = binding.itemEnterpriseImage
        private val name = binding.itemEnterpriseNameText
        private val type = binding.itemEnterpriseTypeText
        private val city = binding.itemEnterpriseCity

        fun bindView(enterprise: EnterpriseDataResponse){
            Glide.with(this.itemView).load(Constants.PHOTO_BASE_URL.plus(enterprise.photo))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(image)
            name.text = enterprise.name
            type.text = enterprise.type.typeName
            city.text = enterprise.city

            itemView.setOnClickListener {
                onItemClickListener.invoke(enterprise)
            }
        }
    }
}