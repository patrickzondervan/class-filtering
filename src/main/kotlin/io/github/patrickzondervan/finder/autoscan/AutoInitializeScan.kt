package io.github.patrickzondervan.finder.autoscan

import io.github.patrickzondervan.finder.ClassFinder
import java.lang.IllegalStateException

abstract class AutoInitializeScan
{
    init
    {
        val filter = ClassFinder.classLoader()
            ?: throw IllegalStateException("Unable to get classes from class loader")

        if (this.javaClass.isAnnotationPresent(AutoScanParent::class.java))
        {
            val parents = this.javaClass.getAnnotationsByType(AutoScanParent::class.java)

            for (parent in parents)
            {
                for (value in parent.value)
                {
                    filter.withParent(value.java)
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
                    filter.withAnnotation(value.java)
                }
            }
        }

        for (clazz in filter.filter())
        {
            clazz.newInstance()
        }
    }
}