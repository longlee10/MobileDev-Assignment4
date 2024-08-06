package app.manohar.roomcrud.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.manohar.roomcrud.Models.Users
import app.manohar.roomcrud.R

class UserListAdapter(
    private val users: List<Users>,
    private val listener: OnItemClickListener

) : RecyclerView.Adapter<UserListAdapter.UserViewModel>() {

    interface OnItemClickListener {
        fun onItemClick(user: Users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_layout, parent, false)
        return UserViewModel(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        holder.bind(users[position])
    }

    inner class UserViewModel(userview: View) : RecyclerView.ViewHolder(userview) {
        private val studentNumber: TextView = userview.findViewById(R.id.studentNumber)
        private val exerciseCode: TextView = userview.findViewById(R.id.exerciseCode)
        private val result: TextView = userview.findViewById(R.id.result)
        private  val mark: TextView = userview.findViewById(R.id.mark)

        fun bind(user: Users) {
            studentNumber.text = "Student Number: ${user.studentNumber}"
            exerciseCode.text = "Exercise Code: ${user.exerciseCode}"
            result.text = "Result: ${user.resultObtained}"
            mark.text = "Mark: ${user.mark}"
        }

        init {
            userview.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(users[position])
                }
            }
        }


    }
}

