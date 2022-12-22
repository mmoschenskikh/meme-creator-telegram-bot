import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.maxultra.meme_creator_telegram_bot.di.MainProvider

fun main() {
    val provider = MainProvider()

    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    botsApi.registerBot(provider.bot)
}
