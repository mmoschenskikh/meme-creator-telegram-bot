package ru.maxultra.meme_creator_telegram_bot.tg

import ru.maxultra.meme_creator_telegram_bot.utils.getEnvOrThrow

internal val TELEGRAM_BOT_TOKEN: String = getEnvOrThrow("TELEGRAM_BOT_TOKEN")
internal val TELEGRAM_BOT_USERNAME: String = getEnvOrThrow("TELEGRAM_BOT_USERNAME")
