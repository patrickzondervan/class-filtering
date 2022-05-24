package io.github.patrickzondervan.finder

typealias Filter = (Class<*>) -> Boolean

class ClassFilter(val classes: List<Class<*>>)
{
    val filters = mutableListOf<Filter>()

    fun withPackage(vararg packages: String): ClassFilter
    {
        return withFilter {
            val currentPackage = it.`package`.name
            var keep = false

            for (current in packages)
            {
                if (!currentPackage.startsWith(current))
                {
                    continue
                }

                keep = true
                break
            }

            keep
        }
    }

    fun withCurrentPackage(calledFrom: Class<*>): ClassFilter
    {
        return withFilter {
            val currentPackage = it.`package`.name
            val providedPackage = calledFrom.`package`.name

            currentPackage.startsWith(providedPackage)
        }
    }

    inline fun <reified T : Any> withCurrentPackage(): ClassFilter
    {
        return withCurrentPackage(T::class.java)
    }

    fun withAnnotation(annotation: Class<out Annotation>): ClassFilter
    {
        return withFilter {
            it.isAnnotationPresent(annotation)
        }
    }

    inline fun <reified T : Annotation> withAnnotation(): ClassFilter
    {
        return withAnnotation(T::class.java)
    }

    fun withParent(parent: Class<*>): ClassFilter
    {
        return withFilter {
//            println("${it.name} - ${it.isAssignableFrom(parent)} - ${parent.isAssignableFrom(it)}")
            parent.isAssignableFrom(it) && !it.isAssignableFrom(parent)
        }
    }

    inline fun <reified T> withParent(): ClassFilter
    {
        return withParent(T::class.java)
    }

    fun withClassName(name: String): ClassFilter
    {
        return withFilter {
            it.name == name
        }
    }

    fun withFilter(filter: Filter): ClassFilter
    {
        filters.add(filter)
        return this
    }

    fun filter(): List<Class<*>>
    {
        return this.classes.filter {
            filters.all { filter -> filter(it) }
        }
    }
}