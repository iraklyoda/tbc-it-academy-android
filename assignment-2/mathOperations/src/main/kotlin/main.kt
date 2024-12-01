fun main() {
    val mathOperator = MathOperator()

    // #1 HCF Finder
    println(mathOperator.findHCF(40, 15)) // returns 5

    // #2 LCM Finder
    println(mathOperator.findLCM(40, 15)) // returns 120

    // #3 '$' symbol checker
    println(mathOperator.checkDollarSymbol("TbcAcademy")) // returns false
    println(mathOperator.checkDollarSymbol("TbcAcad\$emy")) // returns true

    // #4 Recursive sum of even numbers till 100
    println(mathOperator.countEvenNumbersSum()) // returns 2550

    // #5 Number reverser
    println(mathOperator.getReversedNumber(10220)) // returns 2201

    // #6 Palindrome checker
    println(mathOperator.checkPalindrome("airamzissizmaria")) // returns true
    println(mathOperator.checkPalindrome("kotlin")) // returns false

}