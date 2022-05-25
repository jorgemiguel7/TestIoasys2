package com.example.testioasys2.ui.main

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
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.exception.NetworkErrorException
import com.example.testioasys2.databinding.ActivityMainBinding
import com.example.testioasys2.ui.adapter.EnterpriseListAdapter
import com.example.testioasys2.ui.details.DetailsActivity
import com.example.testioasys2.utils.LoadingDialog
import com.example.testioasys2.utils.showAlertDialog
import com.example.testioasys2.presentation.main.MainViewModel
import com.example.testioasys2.utils.onQueryTextChange
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setRecyclerView()
        connectionError()
    }

    companion object{
        private const val USER_SESSION_EXTRA = "USER_SESSION_EXTRA"

        fun getStartIntent(context: Context, userSession: UserSession): Intent{
            return Intent(context, MainActivity::class.java).putExtra(USER_SESSION_EXTRA, userSession)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        searchView.onQueryTextChange {
            getEnterpriseList(it.orEmpty())
        }
    }

    private fun getEnterpriseList(newText: String) = binding.apply{
        if (newText.isNotEmpty()){
            mainGuidanceMessageTextView.isGone = true
            mainRecyclerView.isVisible = true
            val accessToken = intent.getSerializableExtra(USER_SESSION_EXTRA) as UserSession
            viewModel.getEnterprise(newText, userSession = accessToken)
        } else mainRecyclerView.isGone = true

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
                }
            }
        }

        viewModel.loading.observe(this@MainActivity){ isLoading ->
            if (isLoading) LoadingDialog.startLoading(this@MainActivity)
            else LoadingDialog.finishLoading()
        }
    }

    private fun displaySearchResult(enterprises: List<Enterprise>) = binding.apply {
        if (enterprises.isNotEmpty()){
            mainGuidanceMessageTextView.isGone = true
            mainRecyclerView.isVisible = true
        } else {
            mainRecyclerView.isGone = true
            mainGuidanceMessageTextView.text = getString(R.string.main_search_not_found)
            mainGuidanceMessageTextView.isVisible = true
        }
    }

    private fun connectionError() = binding.apply{
        viewModel.errorMessage.observe(this@MainActivity){ exception ->
            when(exception){
                is NetworkErrorException -> showAlertDialog(R.string.internet_connection_failure)
                else -> showAlertDialog(R.string.generic_failure)
            }
        }
    }
}