package testKotlin;
import sorting.ItemsSorting
class Test {

    public fun testMe(): Unit {
        println("Hi from kotlin");
        print("Run Selection Sort from kotlin : ")
        println(ItemsSorting.selectionSort(arrayOf("Pavly", "Bavly", "Pavel", "Amer", "Ahmed", "AAme", "Amy", "Emy"), ItemsSorting.SortAlgorithm.A_Z))
    }

}
