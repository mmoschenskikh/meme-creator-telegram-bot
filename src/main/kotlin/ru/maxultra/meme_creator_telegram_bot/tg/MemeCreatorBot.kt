package ru.maxultra.meme_creator_telegram_bot.tg

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.File
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.PhotoSize
import org.telegram.telegrambots.meta.api.objects.Update
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorBotAsyncInteractions
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreatorFeature

class MemeCreatorBot(
    private val feature: MemeCreatorFeature,
) : TelegramLongPollingBot(), MemeCreatorBotAsyncInteractions {

    override fun getBotToken() = TELEGRAM_BOT_TOKEN

    override fun getBotUsername() = TELEGRAM_BOT_USERNAME

    override fun onUpdateReceived(update: Update?) {
        val message = update?.message ?: return
        feature.accept(Msg.OnTgMessageReceived(message))
    }

    override fun sendDefaultAnswerForUnknownInputAsync(chatId: Long): Deferred<Message> =
        sendMessageAsync(
            chatId = chatId,
            text = """
                Unrecognizable message type received.
                Please send a photo with a caption of no more than 200 characters to make a meme.
            """.trimIndent(),
        )

    override fun getPhotoUrlAsync(photo: PhotoSize): Deferred<String?> {
        val photoPath = photo.filePath
        if (photoPath.isNullOrBlank().not()) return CompletableDeferred(File.getFileUrl(TELEGRAM_BOT_TOKEN, photoPath))
        val getFileCommand = GetFile.builder()
            .fileId(photo.fileId)
            .build()
        return executeAsync(getFileCommand).thenApply { file -> file?.getFileUrl(TELEGRAM_BOT_TOKEN) }.asDeferred()
    }

    override fun sendPhotoAsync(chatId: Long, imageUrl: String): Deferred<Message> {
        val sendPhotoCommand = SendPhoto.builder()
            .chatId(chatId)
            .photo(InputFile(imageUrl))
            .build()
        return executeAsync(sendPhotoCommand).asDeferred()
    }

    override fun notifyErrorOccurredAsync(chatId: Long): Deferred<Message> =
        sendMessageAsync(
            chatId = chatId,
            text = """
                An error occurred. Please try later.
            """.trimIndent(),
        )

    private fun sendMessageAsync(chatId: Long, text: String): Deferred<Message> {
        val messageCommand = SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .build()
        return executeAsync(messageCommand).asDeferred()
    }
}
