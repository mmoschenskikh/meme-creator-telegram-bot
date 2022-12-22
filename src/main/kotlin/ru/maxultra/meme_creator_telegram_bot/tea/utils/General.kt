package ru.maxultra.meme_creator_telegram_bot.tea.utils

import ru.maxultra.meme_creator_telegram_bot.tea.Cancelable

internal fun <T> List<(T) -> Unit>.notifyAll(msg: T) = forEach { listener -> listener.invoke(msg) }

internal fun <T> MutableList<(T) -> Unit>.addListenerAndMakeCancelable(listener: (T) -> Unit): Cancelable {
    add(listener)
    return object : Cancelable {
        override fun cancel() {
            remove(listener)
        }
    }
}
