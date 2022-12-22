package ru.maxultra.meme_creator_telegram_bot.tea

interface EffectHandler<Eff : Any, Msg : Any> : Cancelable {
    fun setListener(listener: (Msg) -> Unit)
    fun handleEffect(eff: Eff)
}

fun <Msg : Any, Eff : Any> IStatelessFeature<Msg, Eff>.wrapWithEffectHandler(
    effectHandler: EffectHandler<Eff, Msg>,
    initialEffects: Set<Eff> = emptySet(),
) = object : IStatelessFeature<Msg, Eff> by this {
    override fun cancel() {
        effectHandler.cancel()
        this@wrapWithEffectHandler.cancel()
    }
}.apply {
    effectHandler.setListener { msg -> accept(msg) }
    listenEffect { eff ->
        effectHandler.handleEffect(eff)
    }
    initialEffects.forEach(effectHandler::handleEffect)
}
