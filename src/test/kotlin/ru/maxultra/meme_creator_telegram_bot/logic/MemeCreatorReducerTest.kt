package ru.maxultra.meme_creator_telegram_bot.logic

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.PhotoSize
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Eff
import ru.maxultra.meme_creator_telegram_bot.logic.MemeCreator.Msg
import ru.maxultra.meme_creator_telegram_bot.model.local.MemeModel
import ru.maxultra.meme_creator_telegram_bot.utils.singleToSet
import kotlin.test.Test
import kotlin.test.assertEquals

private const val CHAT_ID = 999L
private const val IMAGE_URL = "meme://"
private const val CAPTION = "Hello world"

class MemeCreatorReducerTest {

    @Test
    fun `handle start message`() {
        val tgMessage = makeTgMessage(text = "/start")
        val msg = Msg.OnTgMessageReceived(tgMessage)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedEff = Eff.Tg.GreetUser(CHAT_ID).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    @Test
    fun `handle image with caption message`() {
        val tgMessage = makeTgMessage(caption = CAPTION, hasImage = true)
        val msg = Msg.OnTgMessageReceived(tgMessage)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedEff = Eff.Tg.GetPhotoUrl(CHAT_ID, tgMessage.photo.first(), CAPTION).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    @Test
    fun `handle unknown message`() {
        val tgMessage = makeTgMessage(hasImage = true)
        val msg = Msg.OnTgMessageReceived(tgMessage)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedEff = Eff.Tg.UnknownInputDefaultAnswer(CHAT_ID).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    @Test
    fun `should make meme`() {
        val msg = Msg.MakeMeme(CHAT_ID, IMAGE_URL, CAPTION)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedMemeModel = MemeModel(
            topText = null,
            bottomText = CAPTION,
            imageUrl = IMAGE_URL,
        )
        val expectedEff = Eff.Api.MakeMeme(CHAT_ID, expectedMemeModel).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    @Test
    fun `should send meme on make meme success`() {
        val msg = Msg.OnMakeMemeSuccess(CHAT_ID, IMAGE_URL)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedEff = Eff.Tg.SendMeme(CHAT_ID, IMAGE_URL).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    @Test
    fun `should tell about error on meme creation error`() {
        val msg = Msg.OnMakeMemeError(CHAT_ID)
        val actualEff = MemeCreatorReducer.reduce(msg)
        val expectedEff = Eff.Tg.NotifyBotError(CHAT_ID).singleToSet()
        assertEquals(expectedEff, actualEff)
    }

    private fun makeTgMessage(
        text: String? = null,
        caption: String? = null,
        hasImage: Boolean = false,
    ): Message = Message().apply {
        this.chat = Chat(CHAT_ID, "private")
        this.text = text
        this.caption = caption
        if (hasImage) {
            this.photo = listOf(
                PhotoSize(
                    /* fileId = */ "fileId",
                    /* fileUniqueId = */ "fileUniqueId",
                    /* width = */ 800,
                    /* height = */ 600,
                    /* fileSize = */ 1337,
                    /* filePath = */ "filePath"
                )
            )
        }
    }
}
