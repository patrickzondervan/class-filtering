package io.github.patrickzondervan.finder.autoscan

import io.github.patrickzondervan.finder.ClassFinder
import java.lang.IllegalStateException

open class AutoMapScan
{
    open val annotationMap = hashMapOf<Class<out Annotation>, MutableList<Any>>()
    open val parentMap = hashMapOf<Class<*>, MutableList<Any>>()

    init
    {
        if (this.javaClass.isAnnotationPresent(AutoScanParent::class.java))
        {

            val parents = this.javaClass.getAnnotationsByType(AutoScanParent::class.java)

            for (parent in parents)
            {
                for (value in parent.value)
                {
                    val filter = ClassFinder.classLoader()
                        ?: throw IllegalStateException("Unable to get classes from class loader")

                    filter
                        .withParent(value.java)
                        .filter()
                        .forEach {
                            parentMap.putIfAbsent(value.java, mutableListOf())
                            parentMap[value.java]!!.add(it)
                        }
                }
            }
        }

        if (this.javaClass.isAnnotationPresent(AutoScanAnnotation::class.java))
        {
            val annotations = this.javaClass.getAnnotationsByType(AutoScanAnnotation::class.java)

            for (annotation in annotations)
            {
                for (value in annotation.value)
                {
                    val filter = ClassFinder.classLoader()
                        ?: throw IllegalStateException("Unable to get classes from class loader")

                    filter
                        .withParent(value.java)
                        .filter()
                        .forEach {
                            annotationMap.putIfAbsent(value.java, mutableListOf())
                            annotationMap[value.java]!!.add(it)
                        }
                }
            }
        }
    }
}