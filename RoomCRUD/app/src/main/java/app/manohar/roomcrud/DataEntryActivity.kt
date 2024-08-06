package app.manohar.roomcrud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.manohar.roomcrud.Adapters.UserListAdapter
import app.manohar.roomcrud.Models.Users
import app.manohar.roomcrud.Repository.UserRepo
import app.manohar.roomcrud.RoomDatabase.UserDatabase
import app.manohar.roomcrud.ViewModels.UserViewModel
import app.manohar.roomcrud.ViewModels.ViewModelFactory
import app.manohar.roomcrud.databinding.ActivityDataEntryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DataEntryActivity : AppCompatActivity(), UserListAdapter.OnItemClickListener {


    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserListAdapter

    private lateinit var binding: ActivityDataEntryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            binding = DataBindingUtil.setContentView(this, R.layout.activity_data_entry)

            val repository =
                UserRepo(UserDatabase.getDatabaseInstance(applicationContext).userDao())
            val viewModelFactory = ViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

            binding.submitBtn.setOnClickListener {
                insertUser()
                loadUsers()
            }

        } catch (e: Exception) {

        }
    }

    override fun onStart() {
        super.onStart()
        loadUsers()
    }

    private fun initRecyclerview() {
        binding.userRecycler.layoutManager = LinearLayoutManager(this@DataEntryActivity)
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = viewModel.getAllUsers()

            Log.e("Users", ":$users");

            lifecycleScope.launch(Dispatchers.Main) {

                initRecyclerview()
                adapter = UserListAdapter(users,this@DataEntryActivity)
                binding.userRecycler.adapter = adapter
            }
        }
    }

    private fun insertUser() {
        val studentNumber = binding.studentNumberEt.text.toString()
        val exerciseCode = binding.exerciseCodeEt.text.toString()
        val result = binding.resultEt.text.toString()
        val mark = binding.markEt.text.toString()

        Log.e("student_number", ":${studentNumber.toString()}");
        Log.e("exercise_code", ":${exerciseCode.toString()}")
        Log.e("result", ":${result.toString()}")
        Log.e("mark", ": ${mark.toString()}")

        val user = Users(
            studentNumber = studentNumber,
            exerciseCode = exerciseCode,
            resultObtained = result,
            mark = mark)

        viewModel.insertUser(user)

        binding.studentNumberEt.text.clear()
        binding.exerciseCodeEt.text.clear()
        binding.markEt.text.clear()
        binding.resultEt.text.clear()
        binding.studentNumberEt.requestFocus()
    }



    override fun onItemClick(user: Users) {
        showUserDialog(user)
    }


    private fun showUserDialog(user: Users) {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)

        val studentNumberEditText = dialogView.findViewById<EditText>(R.id.studentNumber_et)
        val exerciseCodeEditText = dialogView.findViewById<EditText>(R.id.exerciseCode_et)
        val resultEditText = dialogView.findViewById<EditText>(R.id.result_et)
        val markEditText = dialogView.findViewById<EditText>(R.id.mark_et)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("User Details")

        val alertDialog = dialogBuilder.create()

        studentNumberEditText.setText(user.studentNumber)
        exerciseCodeEditText.setText(user.exerciseCode)
        resultEditText.setText(user.resultObtained)
        markEditText.setText(user.mark)

        dialogView.findViewById<Button>(R.id.cancel_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.delete_btn).setOnClickListener {
            viewModel.deleteUser(user)
            loadUsers()
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.update_btn).setOnClickListener {
            val studentNumber = studentNumberEditText.text.toString()
            val exerciseCode = exerciseCodeEditText.text.toString()
            val result = resultEditText.text.toString()
            val mark = markEditText.text.toString()
            // Process the values here as needed

            val user = Users(id=user.id,
                studentNumber = studentNumber,
                exerciseCode = exerciseCode,
                resultObtained = result,
                mark = mark)
            viewModel.updateUser(user)
            loadUsers()

            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}