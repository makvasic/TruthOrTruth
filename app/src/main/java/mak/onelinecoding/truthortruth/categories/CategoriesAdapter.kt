package mak.onelinecoding.truthortruth.categories

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.item_category.view.*
import mak.onelinecoding.truthortruth.R

class CategoriesAdapter(private val categories: ArrayList<Category> = ArrayList()) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {

    init {
        categories.add(0, Category(0, "Happy Hour"))
        categories.add(1, Category(1, "On The Rocks"))
        categories.add(2, Category(2, "Extra Dirty"))
        categories.add(3, Category(3, "Last Call"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categories[position])
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) = with(itemView) {

            categoryFab.transitionName = "myMoveTransition" + category.id

            setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    categoryFab to "myMoveTransition" + category.id
                )

                findNavController().navigate(
                    CategoriesFragmentDirections.actionCategoriesFragmentToCardsFragment(category),
                    extras
                )
            }

            categoryNameTextView.text = category.name

            when (category.id) {
                0 -> categoryFab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.greenPrimary
                    )
                )
                1 -> categoryFab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.bluePrimary
                    )
                )
                2 -> categoryFab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.redPrimary
                    )
                )
                3 -> categoryFab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.violetPrimary
                    )
                )
            }

            val lp = itemView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
            }
        }
    }
}