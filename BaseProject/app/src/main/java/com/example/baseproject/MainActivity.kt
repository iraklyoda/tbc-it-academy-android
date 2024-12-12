package com.example.baseproject

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import com.example.baseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        buildCard(binding)
        buildNav(binding)
        buildData(binding)
        buildDescription(binding)
        buildButton(binding)
    }

    private fun buildCard(binding: ActivityMainBinding) {
        val card: CardView = binding.cardDestination
        val cardLayoutParams = binding.cardDestination.layoutParams as MarginLayoutParams
        cardLayoutParams.height = resources.getDimensionPixelSize(R.dimen.card_height)
        cardLayoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        cardLayoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        cardLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        card.radius = 25.5f * resources.displayMetrics.density

        val btnLeftArrowParams = binding.btnLeftArrow.layoutParams as MarginLayoutParams
        btnLeftArrowParams.topMargin = resources.getDimensionPixelSize(R.dimen.icon_margin_top)
        btnLeftArrowParams.marginStart = resources.getDimensionPixelSize(R.dimen.card_horizontal_margin)

        val btnBookmarkParams = binding.btnBookmark.layoutParams as MarginLayoutParams
        btnBookmarkParams.topMargin = resources.getDimensionPixelSize(R.dimen.icon_margin_top)
        btnBookmarkParams.marginEnd = resources.getDimensionPixelSize(R.dimen.card_horizontal_margin)

        binding.frameInfo.setPadding(
            resources.getDimensionPixelSize(R.dimen.info_frame_padding_start),
            resources.getDimensionPixelSize(R.dimen.info_frame_padding_vertical),
            resources.getDimensionPixelSize(R.dimen.info_frame_padding_end),
            resources.getDimensionPixelSize(R.dimen.info_frame_padding_vertical)
        )

        binding.textAndes?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_large))
        binding.textPrice?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_medium))
        binding.textSouthAmerica.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_medium))
        binding.textDollarSign?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_20))
        binding.text230?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_26))

        val frameInfoParams = binding.frameInfo.layoutParams as MarginLayoutParams
        frameInfoParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.info_frame_margin_bottom)
        frameInfoParams.leftMargin = resources.getDimensionPixelSize(R.dimen.info_frame_margin_bottom)
        frameInfoParams.rightMargin = resources.getDimensionPixelSize(R.dimen.info_frame_margin_bottom)
        frameInfoParams.height = resources.getDimensionPixelSize(R.dimen.info_frame_height)


        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(ContextCompat.getColor(this@MainActivity, R.color.eerie_black_30))
            cornerRadius = 16 * resources.displayMetrics.density
        }

        binding.frameInfo.background = backgroundDrawable

        val textSouth: MarginLayoutParams = binding.textSouthAmerica.layoutParams as MarginLayoutParams
        textSouth.marginStart = resources.getDimensionPixelSize(R.dimen.location_margin_left)
    }

    private fun buildNav(binding: ActivityMainBinding) {
        val navParams: MarginLayoutParams = binding.navLayout.layoutParams as MarginLayoutParams
        navParams.marginStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        navParams.topMargin = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        val textDetails: MarginLayoutParams = binding.textDetails?.layoutParams as MarginLayoutParams
        textDetails.marginStart = resources.getDimensionPixelSize(R.dimen.details_margin_left)

        binding.textOverview?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_22))
    }

    private fun buildData(binding: ActivityMainBinding) {
        val navData: MarginLayoutParams = binding.dataLayout?.layoutParams as MarginLayoutParams
        navData.marginStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        navData.marginEnd = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        navData.topMargin = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        val textHours: MarginLayoutParams = binding.text8Hours?.layoutParams as MarginLayoutParams
        textHours.marginStart = resources.getDimensionPixelSize(R.dimen.location_margin_left)

        val textTemp: MarginLayoutParams = binding.textTemp?.layoutParams as MarginLayoutParams
        textTemp.marginStart = resources.getDimensionPixelSize(R.dimen.location_margin_left)

        val textReview: MarginLayoutParams = binding.textReview?.layoutParams as MarginLayoutParams
        textReview.marginStart = resources.getDimensionPixelSize(R.dimen.location_margin_left)
    }

    private fun buildDescription(binding: ActivityMainBinding) {
        val textDescriptionParams: MarginLayoutParams = binding.textDescription?.layoutParams as MarginLayoutParams
        textDescriptionParams.topMargin = resources.getDimensionPixelSize(R.dimen.details_margin_left)
        binding.textDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_medium))
    }

    private fun buildButton(binding: ActivityMainBinding) {
        val buttonLayoutParams = binding.buttonLayout?.layoutParams as MarginLayoutParams
        buttonLayoutParams.height = resources.getDimensionPixelSize(R.dimen.button_height)
        buttonLayoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        buttonLayoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        buttonLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(ContextCompat.getColor(this@MainActivity, R.color.black))
            cornerRadius = 16 * resources.displayMetrics.density
        }

        binding.buttonLayout.background = backgroundDrawable
        binding.textBookNow?.setTextColor(ContextCompat.getColor(this, R.color.white))

        val textBookNowParams = binding.textBookNow?.layoutParams as MarginLayoutParams
        textBookNowParams.marginEnd = resources.getDimensionPixelSize(R.dimen.info_frame_padding_start)
    }
}




