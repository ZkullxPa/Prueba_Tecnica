package com.example.prueba_tecnica.core

import android.content.Context
import javax.inject.Inject

class SharedPreference @Inject constructor(
    context: Context
) {
    private val prefs = context.getSharedPreferences("myDtb", Context.MODE_PRIVATE)

    fun saveUserId(userId: String){
        prefs.edit().putString("userId", userId).apply()
    }

    fun getUserId(): String? {
        return prefs.getString("userId", null)
    }

    private fun watchedKey(userId: Long, characterId: Int?) =
        "watched_${userId}_${characterId}"

    fun getWatchedEpisodes(userId: Long, characterId: Int?): Set<Int> {
        val raw = prefs.getStringSet(watchedKey(userId, characterId), emptySet()) ?: emptySet()
        return raw.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun setWatchedEpisodes(userId: Long, characterId: Int?, ids: Set<Int>) {
        prefs.edit()
            .putStringSet(watchedKey(userId, characterId), ids.map { it.toString() }.toSet())
            .apply()
    }

    fun addWatchedEpisode(userId: Long, characterId: Int?, episodeId: Int) {
        val current = getWatchedEpisodes(userId, characterId).toMutableSet()
        if (current.add(episodeId)) {
            setWatchedEpisodes(userId, characterId, current)
        }
    }

    fun removeWatchedEpisode(userId: Long, characterId: Int?, episodeId: Int) {
        val current = getWatchedEpisodes(userId, characterId).toMutableSet()
        if (current.remove(episodeId)) {
            setWatchedEpisodes(userId, characterId, current)
        }
    }
}