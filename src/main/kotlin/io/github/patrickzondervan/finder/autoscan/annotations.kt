package io.github.patrickzondervan.finder.autoscan

import kotlin.reflect.KClass

annotation class AutoScanParent(
    vararg val value: KClass<*>
)

annotation class AutoScanAnnotation(
    vararg val value: KClass<out Annotation>,
)