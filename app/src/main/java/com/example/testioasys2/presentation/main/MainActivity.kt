package com.example.testioasys2.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testioasys2.R
import com.example.testioasys2.data.repository.EnterpriseApiDataSource
import com.example.testioasys2.data.response.EnterpriseDataResponse
import com.example.testioasys2.databinding.ActivityMainBinding
import com.example.testioasys2.presentation.adapter.EnterpriseListAdapter
import com.example.testioasys2.presentation.details.DetailsActivity
import com.example.testioasys2.utils.AlertMessage
import com.example.testioasys2.utils.LoadingDialog
import com.example.testioasys2.viewModel.main.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        viewModel = MainViewModel.ViewModelFactory(EnterpriseApiDataSource())
            .create(MainViewModel::class.java)
        setRecyclerView()
        connectionError()
    }

    companion object{
        fun getStartIntent(context: Context): Intent{
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchInActionBar(menu)
        return true
    }

    private fun setToolbar(){
        setSupportActionBar(binding.mainToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun searchInActionBar(menu: Menu?) = binding.apply {
        val menuItem: MenuItem = menu!!.findItem(R.id.menuSearch)
        val searchView: SearchView = menuItem.actionView as SearchView

        searchView.queryHint = getString(R.string.main_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainGuidanceMessage.isGone = true
                if (newText!!.isNotEmpty()){
                    LoadingDialog.startLoading(this@MainActivity)
                    viewModel.getEnterprise(newText)
                } else {
                    mainRecyclerView.isGone = true
                }
                return false
            }
        })
    }

    private fun setRecyclerView() = binding.apply{
        viewModel.success.observe(this@MainActivity){
            it?.let { enterprises ->
                displaySearchResult(enterprises)
                with(mainRecyclerView){
                    layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = EnterpriseListAdapter(enterprises){ enterprise ->
                        val intent = DetailsActivity.getStratIntent(this@MainActivity, enterprise.name, enterprise.photo, enterprise.description)
                        startActivity(intent)
                    }
                    LoadingDialog.finishLoading()
                }
            }
        }
    }

    private fun displaySearchResult(enterprises: List<EnterpriseDataResponse>) = binding.apply {
        if (enterprises.isNotEmpty()){
            mainGuidanceMessage.isGone = true
            mainRecyclerView.isVisible = true
        } else {
            mainRecyclerView.isGone = true
            mainGuidanceMessage.text = getString(R.string.main_search_not_found)
            mainGuidanceMessage.isVisible = true
        }
    }

    private fun connectionError(){
        viewModel.errorMessage.observe(this){
            it?.let { message ->
                LoadingDialog.finishLoading()
                AlertMessage.showAlertDialog(this, message)
            }
        }
    }
}