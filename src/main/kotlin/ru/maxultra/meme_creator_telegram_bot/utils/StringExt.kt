package ru.maxultra.meme_creator_telegram_bot.utils

fun String.nullIfBlank(): String? = ifBlank { null }
