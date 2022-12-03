package ru.maxultra.meme_creator_telegram_bot.api.config

import ru.maxultra.meme_creator_telegram_bot.api.MemeApiConst

data class ServerConfig(
    val host: String,
    val basePath: String?,
    val token: String,
)

internal val defaultServerConfig = ServerConfig(
    host = MemeApiConst.HOST,
    basePath = MemeApiConst.BASE_PATH,
    token = MEME_API_TOKEN,
)
