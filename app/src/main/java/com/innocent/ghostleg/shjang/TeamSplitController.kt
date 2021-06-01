package com.innocent.ghostleg.shjang

object TeamSplitController {
    fun splitTeam(members: Array<String>): Array<Array<String>> {
        members.shuffle();
        val firstTeamSize = Math.ceil((members.size / 2).toDouble()).toInt();
        val secondTeamSize = members.size - firstTeamSize;
        return arrayOf(members.take(firstTeamSize).toTypedArray(), members.takeLast(secondTeamSize).toTypedArray());
    }
}