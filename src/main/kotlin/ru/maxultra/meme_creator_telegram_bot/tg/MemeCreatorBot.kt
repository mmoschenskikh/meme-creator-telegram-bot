package ru.maxultra.meme_creator_telegram_bot.tg

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorFeature

class MemeCreatorBot(
    private val feature: MemeCreatorFeature,
) : TelegramLongPollingBot() {

    override fun getBotToken() = TELEGRAM_BOT_TOKEN

    override fun getBotUsername() = TELEGRAM_BOT_USERNAME

    override fun onUpdateReceived(update: Update?) {
        val message = update?.message ?: return
        feature.accept(Msg.OnTgMessageReceived(message))
    }
}
