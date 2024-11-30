class MathOperator {


    fun findHCF(x: Int, y: Int): Int? {

        var hcf: Int = 0
        val higher: Int = if (x > y) x else y

        if (x < 0 || y < 0) {
            println("Please enter whole numbers")
            return null
        }

        if (x == 0 && y == 0) {
            println("0/0 is undefined")
            return 0
        }

        for(i in 1..higher) {
            if(x % i == 0 && y % i == 0) {
                hcf = i
            }
        }

        return hcf
    }

    fun findLCM(x: Int, y: Int): Int? {
        var lcm: Int = 0
        var higher: Int = if (x > y) x else y

        if (x < 0 || y < 0) {
            println("Please enter whole numbers")
            return null
        }

        if (x == 0 || y == 0) {
            println("0 has no lcm")
            return null
        }

        var i = 1
        while (true) {
            if (higher % x == 0 && higher % y == 0) {
                lcm = higher
                break
            }
            higher++
        }

        return lcm
    }

    fun checkDollarSymbol(string: String): Boolean {
        return string.contains('$')
    }

    fun countEvenNumbersSum(n: Int = 1, sum: Int = 0): Int {
        if (n <= 100) {
            var even = 0
            if (n % 2 == 0)
                even = n
            return countEvenNumbersSum(n = n+1, sum = sum+even)
        }
        return sum
    }

    fun getReversedNumber(number: Int): Int? {
        if(number < 0) {
            println("Please input whole number")
            return null
        }
        var numberString: String = number.toString()
        var reversedNumberString: String = ""

        for (i in numberString.length-1 downTo 0) {
            reversedNumberString += numberString[i]
        }

        return reversedNumberString.toInt()
    }

    fun checkPalindrome(string: String): Boolean {
        var reversedString: String = ""

        for (i in string.length - 1 downTo 0) {
            reversedString += string[i]
        }

        return string == reversedString
    }
}