import io.github.patrickzondervan.finder.autoscan.AutoInitializeScan
import io.github.patrickzondervan.finder.autoscan.AutoScanAnnotation
import io.github.patrickzondervan.finder.autoscan.AutoScanParent
import org.junit.Test

class AutoScanTest
{
    @Test
    fun test()
    {
        B()
        C()
        D()

        println("")
        println("Auto scanning..")

        AutoScanObject()

        println("")
        println("Auto scanning second...")

        AutoScanObject2()

        println("")
        println("Auto scanning third...")

        AutoScanObject3()
    }
}

@AutoScanParent(A::class)
@AutoScanAnnotation(CA::class)
class AutoScanObject : AutoInitializeScan()

@AutoScanParent(A::class)
class AutoScanObject2 : AutoInitializeScan()

@AutoScanAnnotation(CA::class)
class AutoScanObject3 : AutoInitializeScan()

abstract class A

class B : A()
{
    init
    {
        println("initialized B - extending A")
    }
}

@CA
class C
{
    init
    {
        println("initialized C - annotated by CA")
    }
}

@CA
class D : A()
{
    init
    {
        println("initialized D - extending A and annotated by CA")
    }
}

annotation class CA