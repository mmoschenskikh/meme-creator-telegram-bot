package ru.maxultra.meme_creator_telegram_bot.tea

interface IStatelessFeature<Msg : Any, Eff : Any> {
    fun accept(msg: Msg)
    fun listenEffect(listener: (eff: Eff) -> Unit): Cancelable
    fun cancel()
}
