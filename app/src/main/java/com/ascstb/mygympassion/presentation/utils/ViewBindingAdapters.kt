package com.ascstb.mygympassion.presentation.utils

import android.annotation.SuppressLint
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

@BindingAdapter(
    value = ["imageUrl", "iconResourceName", "roundedStyle", "iconColor", "placeHolder"],
    requireAll = false
)
fun imageUrl(
    view: ImageView,
    imageUrl: String?,
    iconResourceName: String?,
    roundedStyle: Boolean?,
    iconColor: Int?,
    placeHolder: Int = 0
) {
    if (iconResourceName.isNullOrEmpty() && imageUrl.isNullOrEmpty()) {
        Timber.d("_TAG: imageUrl: no image available, $placeHolder")
        return
    }

    try {
        if (!imageUrl.isNullOrEmpty()) {
            if (roundedStyle == true) {
                Glide.with(view).load(imageUrl).apply(
                    RequestOptions.bitmapTransform(
                        RoundedCorners(
                            48
                        )
                    )
                ).into(view)
            } else {
                Glide.with(view).load(imageUrl).into(view)
            }
        } else if (!iconResourceName.isNullOrEmpty()) {
            val drawableId = view.context.getDrawable(
                view.context.resources.getIdentifier(
                    iconResourceName,
                    "drawable",
                    view.context.packageName
                )
            )
            Glide.with(view).load(drawableId).into(view)

            iconColor?.let {
                view.setColorFilter(it)
            }
        }
    } catch (e: Exception) {
        Timber.d("ViewBindingAdapter_TAG: iconUrl: $imageUrl, iconResourceName: $iconResourceName")
    }
}

@BindingAdapter(value = ["htmlContent"], requireAll = false)
fun htmlContent(tv: TextView, htmlContent: String?) {
    tv.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("android:visibility")
fun visibility(view: View, visible: Boolean?) = when (visible) {
    true -> view.visibility = View.VISIBLE
    else -> view.visibility = View.GONE
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["linkedText", "endText", "highlightedText"], requireAll = false)
fun linkedText(view: TextView, text: String, endText: String, highlightedText: String) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) = widget.setOnClickListener { }

        override fun updateDrawState(ds: TextPaint) {
            //ds.color = view.context.getColor(R.color.colorBlue2)
            ds.isUnderlineText = true
        }
    }

    Timber.d("_TAG: linkedText: startText: $text, highlightText: $highlightedText, endText: $endText")
    val fullString = "$text $highlightedText $endText"

    // Some sanity checks
    if (highlightedText.isBlank()) {
        view.text = fullString
        return
    }

    val sb = SpannableStringBuilder(fullString)
    val clickableSpanStart = sb.indexOf(highlightedText)

    if (clickableSpanStart > -1) {
        sb.setSpan(
            clickableSpan,
            clickableSpanStart, clickableSpanStart + highlightedText.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    view.text = sb
}