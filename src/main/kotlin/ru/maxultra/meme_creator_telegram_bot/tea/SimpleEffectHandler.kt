package ru.maxultra.meme_creator_telegram_bot.tea

abstract class SimpleEffectHandler<Eff : Any, Msg : Any> : EffectHandler<Eff, Msg> {
    private val effCancelables = mutableListOf<Cancelable>()
    private var listener: ((Msg) -> Unit)? = null

    final override fun cancel() {
        effCancelables.forEach { it.cancel() }
        effCancelables.clear()
        listener = null
    }

    final override fun handleEffect(eff: Eff) {
        listener?.let { invoke(eff, it) }
    }

    final override fun setListener(listener: (Msg) -> Unit) {
        this.listener = listener
    }

    abstract fun invoke(eff: Eff, listener: (Msg) -> Unit): Cancelable
}
