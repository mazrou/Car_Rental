package com.example.a1171832jumanafinalproj.ui.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a1171832jumanafinalproj.DataBaseHelper
import com.example.a1171832jumanafinalproj.R
import com.example.a1171832jumanafinalproj.User

class CostumerListFragment : Fragment(R.layout.fragment_costumer_list)  , CostumerAdapter.Interaction{

    lateinit var recyclerViewAdapter : CostumerAdapter
    lateinit var  recyclerView: RecyclerView
    var dataBaseHelper: DataBaseHelper? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter = CostumerAdapter(this)
        dataBaseHelper = DataBaseHelper(requireContext(), "database", null, 1)
        val cursor = dataBaseHelper?.allUsers
        val userList : ArrayList<User> =  ArrayList()
        while (cursor!!.moveToNext()){
            val user = User()
            val email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"))
            val isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow("IS_ADMIN"))
            user.email = email
            if (isAdmin ==0 ) {
                userList.add(user)
            }
        }
        recyclerView = requireActivity().findViewById(R.id.costumer_rv)
        recyclerView.also {
            it.adapter = recyclerViewAdapter
            it.layoutManager  = LinearLayoutManager(requireContext())
        }
        recyclerViewAdapter.submitList(userList)

    }




    override fun onItemSelected(position: Int, item: User) {

    }

}