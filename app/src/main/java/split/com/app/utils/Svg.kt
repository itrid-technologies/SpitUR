package split.com.app.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import split.com.app.R

object Svg {

    fun loadUrl(url: String, imgView: ImageView) {

        val imageLoader = ImageLoader.Builder(imgView.context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(imgView.context)
            .placeholder(R.color.images_placeholder)
            .size(30, 30)
            .data(url)
            .target(imgView)
            .build()

        imageLoader.enqueue(request)
    }

}