package com.rgos_developer.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rgos_developer.convidados.R
import com.rgos_developer.convidados.constants.DataBaseConstants
import com.rgos_developer.convidados.databinding.ActivityGuestFormBinding
import com.rgos_developer.convidados.model.GuestModel
import com.rgos_developer.convidados.viewmodel.GuestFormViewModel


class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        binding.buttonSalve.setOnClickListener(this)
        binding.radioButtonPresent.isChecked = true

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_salve) {
            val name = binding.editName.text.toString()
            val presence = binding.radioButtonPresent.isChecked

            val model = GuestModel(guestId, name, presence)
            viewModel.save(model)

        }
    }

    private fun observe() {
        viewModel.guest.observe(this) {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioButtonPresent.isChecked = true
            } else {
                binding.radioButtonAbsent.isChecked = true
            }
        }


        viewModel.saveGuest.observe(this){
            if (it != ""){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    private fun loadData() {
        val bundle = intent.extras

        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}
