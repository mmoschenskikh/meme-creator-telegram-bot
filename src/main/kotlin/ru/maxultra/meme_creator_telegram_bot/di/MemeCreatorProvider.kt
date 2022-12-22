package ru.maxultra.meme_creator_telegram_bot.di

import kotlinx.coroutines.CoroutineScope
import ru.maxultra.meme_creator_telegram_bot.data.MemeGeneratorRepository
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorBotAsyncInteractions
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorReducer
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorTelegramEffectHandler
import ru.maxultra.meme_creator_telegram_bot.logic.MemeGeneratorEffectHandler
import ru.maxultra.meme_creator_telegram_bot.tea.StatelessFeature
import ru.maxultra.meme_creator_telegram_bot.tea.wrapWithEffectHandler

class MemeCreatorProvider(
    backgroundWorkScope: CoroutineScope,
    memeGeneratorRepository: MemeGeneratorRepository,
    botAsyncInteractionsProvider: () -> MemeCreatorBotAsyncInteractions?,
) {

    val feature = StatelessFeature(
        reducer = MemeCreatorReducer::reduce,
    ).wrapWithEffectHandler(
        effectHandler = MemeCreatorTelegramEffectHandler(
            scope = backgroundWorkScope,
            botAsyncInteractionsProvider = botAsyncInteractionsProvider,
        ),
    ).wrapWithEffectHandler(
        effectHandler = MemeGeneratorEffectHandler(
            scope = backgroundWorkScope,
            repository = memeGeneratorRepository,
        ),
    )
}
