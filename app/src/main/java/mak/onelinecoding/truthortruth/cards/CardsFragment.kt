package mak.onelinecoding.truthortruth.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_cards.*
import mak.onelinecoding.truthortruth.R
import mak.onelinecoding.truthortruth.categories.Category


class CardsFragment : Fragment(), CardsAdapter.Listener {

    private lateinit var category: Category
    private val cardsAdapter: CardsAdapter by lazy {
        CardsAdapter(listener = this)
    }
    private val cardsViewModel: CardsViewModel by lazy {
        ViewModelProvider(this).get(CardsViewModel::class.java)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        var selectedPosition = 0
        override fun onPageSelected(position: Int) {
            cardsViewModel.currentPosition = position
            selectedPosition = position
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (positionOffset == 0f) {
                if (CardsViewModel.ROUND_SIZE <= selectedPosition) {
                    onRoundFinish()
                } else {
                    selectedPosition++
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = CardsFragmentArgs.fromBundle(arguments!!).category

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                categoryFab?.isExpanded = true
            }

            override fun onTransitionResume(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionStart(transition: Transition) {
            }

        })
        sharedElementEnterTransition = transition

        when (category.id) {
            0 -> {
                activity?.setTheme(R.style.GreenTheme)
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(context!!, R.color.greenPrimaryDark)
                activity?.window?.navigationBarColor =
                    ContextCompat.getColor(context!!, R.color.greenPrimaryDark)
            }
            1 -> {
                activity?.setTheme(R.style.BlueTheme)
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(context!!, R.color.bluePrimaryDark)
                activity?.window?.navigationBarColor =
                    ContextCompat.getColor(context!!, R.color.bluePrimaryDark)

            }
            2 -> {
                activity?.setTheme(R.style.RedTheme)
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(context!!, R.color.redPrimaryDark)
                activity?.window?.navigationBarColor =
                    ContextCompat.getColor(context!!, R.color.redPrimaryDark)

            }
            3 -> {
                activity?.setTheme(R.style.VioletTheme)
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(context!!, R.color.violetPrimaryDark)
                activity?.window?.navigationBarColor =
                    ContextCompat.getColor(context!!, R.color.violetPrimaryDark)

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                categoryFab.isExpanded = false

                categoryFab.postDelayed({ findNavController().navigateUp() }, 300)
            }

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        categoryFab.transitionName = "myMoveTransition" + category.id

        viewPager2.adapter = cardsAdapter
        viewPager2.setPageTransformer(FlipPageTransformer())
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback)

        cardsViewModel.questionsLiveData.observe(viewLifecycleOwner, Observer<List<String>> {
            cardsAdapter.setData(it)
            viewPager2.currentItem = cardsViewModel.currentPosition
        })

        cardsViewModel.getQuestionsForRound(category.id)
    }

    override fun onDestroyView() {
        viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback)
        super.onDestroyView()
    }

    override fun onCardClicked() {
        viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
    }

    override fun onRoundFinish() {
        AlertDialog.Builder(context!!)
            .setMessage("Bravo! You finished the round. Up for more?")
            .setNegativeButton("No") { dialog, which ->
                activity?.onBackPressed()
            }
            .setNeutralButton("Cancel", null)
            .setPositiveButton("Yes") { dialog, which ->
                viewPager2.currentItem = 0
                cardsViewModel.getQuestionsForRound(category.id, true)
            }
            .show()
    }

}