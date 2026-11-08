package dev.deftu.bettergamma

import dev.deftu.bettergamma.config.BetterGammaConfig
import kotlin.math.max
import kotlin.math.min

/** How far fullbright has faded in, from 0 (off) to 1 (fully on) with the chosen easing applied */
object FullbrightFade {
    private var progress = 0f
    private var lastUpdate = 0L

    val value: Float
        get() {
            val target = if (BetterGammaConfig.enabled && BetterGammaConfig.fullbright) 1f else 0f
            val now = System.nanoTime()
            val durationNanos = BetterGammaConfig.fadeDuration * 1_000_000_000f
            // Fullbright saved as on should already be on at launch, not fade in
            progress = if (!BetterGammaConfig.smoothFade || lastUpdate == 0L) {
                target
            } else {
                val step = (now - lastUpdate) / durationNanos
                if (progress < target) min(target, progress + step) else max(target, progress - step)
            }

            lastUpdate = now
            return BetterGammaConfig.fadeEasing.easing.transform(progress)
        }

    // Read from the raw progress, since some easings touch 0 or 1 partway through a fade
    val isFading: Boolean
        get() {
            value
            return progress > 0f && progress < 1f
        }
}
