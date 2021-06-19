package mak.onelinecoding.truthortruth.cards


import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class FlipPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val percentage = 1 - abs(position)
        page.cameraDistance = 12000f
        setVisibility(page, position)
        setTranslation(page)
        setSize(page, position, percentage)
        setRotation(page, position, percentage)
    }

    private fun setVisibility(page: View, position: Float) {
        if (-0.5 < position && position < 0.5) {
            page.visibility = View.VISIBLE
        } else {
            page.visibility = View.INVISIBLE
        }
    }

    private fun setTranslation(page: View) {
        val viewPager2 = page.parent.parent as ViewPager2
        val scroll = viewPager2.scrollX - page.left
        page.translationX = scroll.toFloat()
    }

    private fun setSize(page: View, position: Float, percentage: Float) {
        page.scaleX = if (position != 0f && position != 1f) percentage else 1f
        page.scaleY = if (position != 0f && position != 1f) percentage else 1f
    }

    private fun setRotation(page: View, position: Float, percentage: Float) {
        if (position > 0) {
            page.rotationY = -180 * (percentage + 1)
        } else {
            page.rotationY = 180 * (percentage + 1)
        }
    }


}