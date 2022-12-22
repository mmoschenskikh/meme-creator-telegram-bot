package ru.maxultra.meme_creator_telegram_bot.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.maxultra.meme_creator_telegram_bot.data.MemeGeneratorRepository
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Eff
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.tea.Cancelable
import ru.maxultra.meme_creator_telegram_bot.tea.SimpleEffectHandler
import ru.maxultra.meme_creator_telegram_bot.tea.toCancelable

class MemeGeneratorEffectHandler(
    private val scope: CoroutineScope,
    private val repository: MemeGeneratorRepository,
) : SimpleEffectHandler<Eff, Msg>() {

    override fun invoke(eff: Eff, listener: (Msg) -> Unit): Cancelable = when (eff) {
        !is Eff.Api -> Job()
        is Eff.Api.MakeMeme -> makeMeme(eff, listener)
    }.toCancelable()

    private fun makeMeme(eff: Eff.Api.MakeMeme, listener: (Msg) -> Unit) = scope.launch {
        val memeUrl = try {
            withContext(Dispatchers.IO) { repository.generateMeme(eff.meme) }
        } catch (e: Exception) {
            null
        }
        val msg = if (memeUrl.isNullOrBlank()) {
            Msg.OnMakeMemeError(eff.chatId)
        } else {
            Msg.OnMakeMemeSuccess(eff.chatId, memeUrl)
        }
        listener.invoke(msg)
    }
}
