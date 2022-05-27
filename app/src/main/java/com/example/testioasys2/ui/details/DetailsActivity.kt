package com.example.testioasys2.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testioasys2.databinding.ActivityDetailsBinding
import com.example.testioasys2.ui.base.BaseActivity
import com.example.testioasys2.utils.Constants

class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.detailsToolbar, intent.getStringExtra(EXTRA_NAME)!!, true)
        setDetails()
    }

    companion object{
        private const val EXTRA_NAME = "EXTRA_NAME"
        private const val EXTRA_PHOTO = "EXTRA_PHOTO"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStartIntent(context: Context, name: String, photo: String, description: String): Intent{
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_PHOTO, photo)
                putExtra(EXTRA_DESCRIPTION, description)
            }
        }
    }

    private fun setDetails() {
        Glide.with(this@DetailsActivity).load(Constants.PHOTO_BASE_URL
            .plus(intent.getStringExtra(EXTRA_PHOTO)))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.detailsPhotoImageView)
        binding.detailsDescriptionTextView.text = intent.getStringExtra(EXTRA_DESCRIPTION)
    }
}