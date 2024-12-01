fun main() {

    // #1 HCF Finder
    println(MathOperator.findHCF(40, 15)) // returns 5

    // #2 LCM Finder
    println(MathOperator.findLCM(40, 15)) // returns 120

    // #3 '$' symbol checker
    println(MathOperator.checkDollarSymbol("TbcAcademy")) // returns false
    println(MathOperator.checkDollarSymbol("TbcAcad\$emy")) // returns true

    // #4 Recursive sum of even numbers till 100
    println(MathOperator.countEvenNumbersSum()) // returns 2550

    // #5 Number reverser
    println(MathOperator.getReversedNumber(10220)) // returns 2201

    // #6 Palindrome checker
    println(MathOperator.checkPalindrome("airamzissizmaria")) // returns true
    println(MathOperator.checkPalindrome("kotlin")) // returns false

}