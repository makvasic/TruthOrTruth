package mak.onelinecoding.truthortruth.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mak.onelinecoding.truthortruth.Questions

class CardsViewModel : ViewModel() {

    private val _questionsLiveData = MutableLiveData<List<String>>()
    val questionsLiveData: LiveData<List<String>> = _questionsLiveData

    var currentPosition = 0

    fun getQuestionsForRound(categoryId: Int, refresh: Boolean = false) {

        if (_questionsLiveData.value != null && !refresh) return

        val questions = when (categoryId) {
            0 -> Questions.HAPPY_HOUR
            1 -> Questions.ON_THE_ROCKS
            2 -> Questions.EXTRA_DIRTY
            3 -> Questions.LAST_CALL
            else -> ArrayList()
        }

        questions.shuffle()
        val roundQuestions = ArrayList<String>()
        roundQuestions.addAll(
            questions.subList(
                0,
                ROUND_SIZE
            )
        )
        _questionsLiveData.value = roundQuestions
    }

    companion object {
        const val ROUND_SIZE = 10
    }
}