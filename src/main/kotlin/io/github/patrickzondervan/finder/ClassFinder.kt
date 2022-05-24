package io.github.patrickzondervan.finder

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.stream.Collectors

object ClassFinder
{
    fun getLoadedClasses(): Vector<Class<*>>
    {
        val classLoader = Thread.currentThread().contextClassLoader
        val classesField = ClassLoader::class.java.getDeclaredField("classes")
            .apply {
                this.isAccessible = true
            }

        return (classesField.get(classLoader) as Vector<Class<*>>).clone() as Vector<Class<*>>
    }

    fun classLoader(): ClassFilter?
    {
        val loader = ClassLoader.getSystemClassLoader()
        val input = loader.getResourceAsStream("") ?: return null

        val reader = BufferedReader(InputStreamReader(input))

        return ClassFilter(
            getLoadedClasses()
        )
    }

    inline fun <reified T : Any> classLoaderFilter(): ClassFilter?
    {
        return this.classLoader()
            ?.withCurrentPackage<T>()
    }

    fun fromClassList(classes: List<Class<*>>): ClassFilter
    {
        return ClassFilter(classes)
    }
}