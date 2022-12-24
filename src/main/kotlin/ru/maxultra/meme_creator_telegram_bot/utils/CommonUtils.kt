package ru.maxultra.meme_creator_telegram_bot.utils

fun getEnvOrThrow(name: String) =
    System.getenv(name)?.ifBlank { null } ?: throw NoSuchElementException("Environment variable $name was not found")
