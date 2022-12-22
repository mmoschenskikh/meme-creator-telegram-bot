package ru.maxultra.meme_creator_telegram_bot.logic

import org.telegram.telegrambots.meta.api.objects.Message
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Eff
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.model.local.MemeModel
import ru.maxultra.meme_creator_telegram_bot.tg.Command
import ru.maxultra.meme_creator_telegram_bot.tg.CommandMatcher.asBotCommand
import ru.maxultra.meme_creator_telegram_bot.utils.singleToSet

object MemeCreatorReducer {

    fun reduce(msg: Msg): Set<Eff> = when (msg) {
        is Msg.OnTgMessageReceived -> handleMessageReceived(msg.message)
        is Msg.MakeMeme -> handleMakeMeme(msg)
        is Msg.OnMakeMemeSuccess -> handleMakeMemeSuccess(msg)
        is Msg.OnMakeMemeError -> handleMakeMemeError(msg)
    }

    private fun handleMessageReceived(message: Message): Set<Eff> {
        val command = message.text?.asBotCommand()
        if (command != null) return handleBotCommand(message.chatId, command)

        val photo = message.photo?.maxBy { it.height }
        val hasNoCaption = message.caption.isNullOrBlank()
        if (photo == null || hasNoCaption) return Eff.Tg.UnknownInputDefaultAnswer(message.chatId).singleToSet()
        return Eff.Tg.GetPhotoUrl(message.chatId, photo, message.caption).singleToSet()
    }

    private fun handleMakeMeme(msg: Msg.MakeMeme): Set<Eff> {
        val meme = MemeModel(
            topText = null,
            bottomText = msg.caption,
            imageUrl = msg.imageUrl,
        )
        return Eff.Api.MakeMeme(msg.chatId, meme).singleToSet()
    }

    private fun handleMakeMemeSuccess(msg: Msg.OnMakeMemeSuccess) =
        Eff.Tg.SendMeme(msg.chatId, msg.memeUrl).singleToSet()

    private fun handleMakeMemeError(msg: Msg.OnMakeMemeError) =
        Eff.Tg.NotifyBotError(msg.chatId).singleToSet()

    private fun handleBotCommand(chatId: Long, command: Command) = when (command) {
        Command.START -> Eff.Tg.GreetUser(chatId).singleToSet()
    }
}
