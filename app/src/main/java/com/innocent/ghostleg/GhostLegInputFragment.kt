package com.innocent.ghostleg

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.FragmentGhostLegInputBinding
import com.innocent.ghostleg.GhostLegInputListener

const val MAX_MEMBER_COUNT: Int = 10
const val MIN_MEMBER_COUNT: Int = 2

class GhostLegInputFragment : Fragment() {
    private lateinit var binding: FragmentGhostLegInputBinding
    private var listener: GhostLegInputListener? = null
    private var playerCount: Int = MIN_MEMBER_COUNT
    private lateinit var playerListAdapter: HrgwonAdapter
    private lateinit var resultListAdapter: HrgwonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGhostLegInputBinding.inflate(inflater, container, false)

        initPlayListView()

        binding.btnPlay.setOnClickListener {
            listener?.onCompleteGhostLegInput(
                Integer.parseInt(binding.memberCounter.tvNum.text.toString()),
                (playerListAdapter.dataList.map { data -> data.name }).toTypedArray(),
                (resultListAdapter.dataList.map { data -> data.name }).toTypedArray()
            )
        }
        return binding.root
    }

    private fun initPlayListView() {
        binding.memberCounter.tvTitle.text = "참가 인원"
        playerListAdapter = HrgwonAdapter(context!!, arrayListOf())
        resultListAdapter = HrgwonAdapter(context!!, arrayListOf())
        binding.playerListView.adapter = playerListAdapter
        binding.resultListView.adapter = resultListAdapter
        binding.memberCounter.tvTitle.text = "당첨 인원"

        binding.memberCounter.btnIncrease.setOnClickListener {
            if (playerCount < com.innocent.ghostleg.drawlots.MAX_MEMBER_COUNT) {
                playerCount++
                updateView()
            }
        }
        binding.memberCounter.btnDecrease.setOnClickListener {
            if (playerCount > com.innocent.ghostleg.drawlots.MIN_MEMBER_COUNT) {
                playerCount--
                updateView()
            }
        }

        updateView()
    }

    private fun updateView(beforeCount: Int = playerListAdapter.count) {
        binding.memberCounter.tvNum.text = playerCount.toString()
        if (beforeCount < playerCount) {
            for (i in beforeCount until playerCount) {
                playerListAdapter.addItem(Data(i + 1, "사람${i + 1}"))
                resultListAdapter.addItem(Data(i + 1, "결과${i + 1}"))
            }
        } else if (beforeCount > playerCount) {
            for (i in playerCount until beforeCount) {
                playerListAdapter.removeItem()
                resultListAdapter.removeItem()
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GhostLegInputListener) {
            listener = context
        }
    }
}

interface GhostLegInputListener {
    fun onCompleteGhostLegInput(totalCount: Int, playerList: Array<String>, resultList: Array<String>)
}