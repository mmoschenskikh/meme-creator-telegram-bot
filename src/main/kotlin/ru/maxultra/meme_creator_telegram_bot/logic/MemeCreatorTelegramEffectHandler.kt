package ru.maxultra.meme_creator_telegram_bot.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.PhotoSize
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Eff
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.tea.Cancelable
import ru.maxultra.meme_creator_telegram_bot.tea.SimpleEffectHandler
import ru.maxultra.meme_creator_telegram_bot.tea.toCancelable
import ru.maxultra.meme_creator_telegram_bot.utils.nullIfBlank

class MemeCreatorTelegramEffectHandler(
    private val scope: CoroutineScope,
    private val botAsyncInteractionsProvider: () -> MemeCreatorBotAsyncInteractions?,
) : SimpleEffectHandler<Eff, Msg>() {

    private val botInteractions
        get() = botAsyncInteractionsProvider.invoke()

    override fun invoke(eff: Eff, listener: (Msg) -> Unit): Cancelable = when (eff) {
        !is Eff.Tg -> Job()
        is Eff.Tg.GetPhotoUrl -> getPhotoUrl(eff, listener)
        is Eff.Tg.SendMeme -> sendMeme(eff)
        is Eff.Tg.NotifyBotError -> notifyBotError(eff)
        is Eff.Tg.UnknownInputDefaultAnswer -> sendDefaultAnswer(eff)
    }.toCancelable()

    private fun getPhotoUrl(eff: Eff.Tg.GetPhotoUrl, listener: (Msg) -> Unit) = scope.launch {
        botInteractions
            ?.getPhotoUrlAsync(eff.photo)
            ?.await()
            ?.nullIfBlank()
            ?.let { url ->
                listener.invoke(
                    Msg.MakeMeme(
                        chatId = eff.chatId,
                        imageUrl = url,
                        caption = eff.caption,
                    )
                )
            }
    }

    private fun sendMeme(eff: Eff.Tg.SendMeme) = scope.launch {
        botInteractions
            ?.sendPhotoAsync(eff.chatId, eff.imageUrl)
            ?.await()
    }

    private fun notifyBotError(eff: Eff.Tg.NotifyBotError) = scope.launch {
        botInteractions
            ?.notifyErrorOccurredAsync(eff.chatId)
            ?.await()
    }

    private fun sendDefaultAnswer(eff: Eff.Tg.UnknownInputDefaultAnswer) = scope.launch {
        botInteractions
            ?.sendDefaultAnswerForUnknownInputAsync(eff.chatId)
            ?.await()
    }
}

interface MemeCreatorBotAsyncInteractions {
    fun sendDefaultAnswerForUnknownInputAsync(chatId: Long): Deferred<Message>
    fun getPhotoUrlAsync(photo: PhotoSize): Deferred<String?>
    fun sendPhotoAsync(chatId: Long, imageUrl: String): Deferred<Message>
    fun notifyErrorOccurredAsync(chatId: Long): Deferred<Message>
}
