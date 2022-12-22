package ru.maxultra.meme_creator_telegram_bot.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.maxultra.meme_creator_telegram_bot.api.config.defaultServerConfig
import ru.maxultra.meme_creator_telegram_bot.data.MemeGeneratorRepository
import ru.maxultra.meme_creator_telegram_bot.tg.MemeCreatorBot


class MainProvider {

    private val backgroundWorkScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val apiProvider by lazy { MemeApiProvider(defaultServerConfig) }
    private val memeApi by lazy { apiProvider.memeApi }

    private val memeGeneratorRepository by lazy { MemeGeneratorRepository(memeApi) }

    private val memeCreatorProvider: MemeCreatorProvider by lazy {
        MemeCreatorProvider(
            backgroundWorkScope = backgroundWorkScope,
            memeGeneratorRepository = memeGeneratorRepository,
            botAsyncInteractionsProvider = { bot },
        )
    }

    private val memeCreatorFeature by lazy { memeCreatorProvider.feature }

    val bot by lazy { MemeCreatorBot(memeCreatorFeature) }
}
