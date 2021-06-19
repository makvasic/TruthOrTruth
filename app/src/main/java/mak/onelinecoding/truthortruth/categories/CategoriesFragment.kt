package mak.onelinecoding.truthortruth.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_categories.*
import mak.onelinecoding.truthortruth.R

class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoriesRecyclerView.adapter = CategoriesAdapter()
        postponeEnterTransition()
        categoriesRecyclerView.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    override fun onStart() {
        super.onStart()
        activity?.setTheme(R.style.AppTheme)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
    }
}