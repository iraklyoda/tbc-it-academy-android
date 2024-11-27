import java.util.*

fun main() {
    var programActive: Boolean = true

    while(programActive) {
        println("Start!")

        println("Input the value of X")
        val x: String? = readlnOrNull()
        println("Input the value of Y")
        val y: String? = readlnOrNull()

        var xValue: Int = getNumbers(x)
        var yValue: Int = getNumbers(y)

        if (yValue != 0)
            println("$xValue divided by $yValue is ${xValue / yValue.toFloat()}")
        else
            println("Can't divide on 0")

        while(true) {
            println("Would you like to re-run the program? <Y/N>")
            val answer = readLine()

            when (answer?.uppercase()) {
                "N" -> {
                    println("End")
                    programActive = false
                    break
                }
                "Y" -> {
                    break
                }
                else -> println("Please input either Y or N")
            }
        }
    }

}

fun getNumbers(input: String?): Int {
    var result = ""
    if (input != null) {

        for (character in input) {
            if(character.digitToIntOrNull() != null) {
                result += character
            }
        }

        if(result != "") {
            return result.toInt()
        }
    }
    return 0
}