package dev.deftu.bettergamma.config

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.LinearEasing

// Easings that overshoot past 0 or 1 are left out, since they would push the brightness out of range
enum class FadeEasing(val label: String, val easing: Easing) {
    SMOOTH("Smooth", EaseInOut),
    LINEAR("Linear", LinearEasing),
    EASE_IN("Ease in", EaseIn),
    EASE_OUT("Ease out", EaseOut),
    SINE("Sine", EaseInOutSine),
    CUBIC("Cubic", EaseInOutCubic),
    EXPO("Expo", EaseInOutExpo),
    BOUNCE("Bounce", EaseOutBounce),
}
