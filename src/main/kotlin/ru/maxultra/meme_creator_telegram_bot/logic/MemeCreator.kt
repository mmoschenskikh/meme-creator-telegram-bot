package ru.maxultra.meme_creator_telegram_bot.logic

import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.PhotoSize
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Eff
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.model.local.MemeModel
import ru.maxultra.meme_creator_telegram_bot.tea.IStatelessFeature

typealias MemeCreatorFeature = IStatelessFeature<Msg, Eff>

object MemeCreator {

    sealed class Msg {
        data class OnTgMessageReceived(val message: Message) : Msg()
        data class MakeMeme(val chatId: Long, val imageUrl: String, val caption: String) : Msg()
        data class OnMakeMemeSuccess(val chatId: Long, val memeUrl: String) : Msg()
        data class OnMakeMemeError(val chatId: Long) : Msg()
    }

    sealed class Eff {
        sealed class Tg : Eff() {
            data class GetPhotoUrl(val chatId: Long, val photo: PhotoSize, val caption: String) : Tg()
            data class SendMeme(val chatId: Long, val imageUrl: String) : Tg()
            data class NotifyBotError(val chatId: Long) : Tg()
            data class UnknownInputDefaultAnswer(val chatId: Long) : Tg()
        }

        sealed class Api : Eff() {
            data class MakeMeme(val chatId: Long, val meme: MemeModel) : Api()
        }
    }
}
