package mak.onelinecoding.truthortruth.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import mak.onelinecoding.truthortruth.R

class CardsAdapter(
    private val questions: ArrayList<String> = ArrayList(),
    private val listener: Listener
) :
    RecyclerView.Adapter<CardsAdapter.CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (position == questions.size - 1) {
                listener.onRoundFinish()
                return@setOnClickListener
            }
            listener.onCardClicked()
        }
        holder.bind(questions[position])
    }

    fun setData(questions: List<String>) {
        this.questions.clear()
        this.questions.addAll(questions)

        notifyDataSetChanged()
    }

    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: String) = with(itemView) {
            questionTextView.text = question
        }
    }

    interface Listener {
        fun onCardClicked()
        fun onRoundFinish()
    }
}
