package com.innocent.ghostleg

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.FragmentDrawLotsInputBinding

class DrawLotsInputFragment : Fragment() {
    private lateinit var binding: FragmentDrawLotsInputBinding
    private lateinit var listener: DrawLotsInputListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawLotsInputBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener {
            listener.onCompleteDrawLotsInput(
                Integer.parseInt(binding.totalCountInput.text.toString()),
                Integer.parseInt(binding.selectCountInput.text.toString())
            )
        }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DrawLotsInputListener) {
            listener = context
        }
    }
}

interface DrawLotsInputListener {
    fun onCompleteDrawLotsInput(totalCount: Int, selectCount: Int)
}