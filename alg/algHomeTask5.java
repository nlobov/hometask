/*
 * Домашнее задание курса java algoritms по уроку 5 "Рекурсия"
 * dated Jul 21, 2019
 */

public class algHomeTask5 {
    //Дано целое число a и натуральное число b. Возвести a в степень b.
    //a^b = a * a * ... * a - b раз
    //b - четное число!
    private final static long A = 2;
    private final static long B = 62;
    public static void main(String[] args) {
        try {
            System.out.println("Вычисление с помощью цикла: " + A + "^" + B + " = " + exp(A, B));
            System.out.println("Вычисление с помощью рекурсии: " + A + "^" + B + " = " + expReq(A, B));
        }
        catch (RuntimeException e){
            System.out.println("Произошел RuntimeException: "+e.getMessage());
        }
    }
    // Возведение в степень c помощью рекурсии
    public static long expReq(long a, long b) {
        if (b < 0) {
            throw new RuntimeException("Метод не предназначен для расчета возведения числа "+ a +" в отрицательную степень "+b);
        }
        else if (b == 1) {
            return a;
        } else if (a == 0) {
            return 0;
        } else if (b == 0) {
            return 1;
        }
        // рекурсивные случаи
        else if (b % 2 == 0) {
            long aTmp = expReq(a, b / 2);
            if (aTmp <= 0 ) {
                throw new RuntimeException("Метод не предназначен для расчета возведения числа "+ A +" в степень "+B+". Результат выше диапазона long");
            }
            return aTmp * aTmp;
        } else {
            long tmp = a * expReq(a, b - 1);
            if (tmp <= 0 ) {
                throw new RuntimeException("Метод не предназначен для расчета возведения числа "+ A +" в степень "+B+". Результат выше диапазона long");
            }
            return tmp;
        }
    }

    //Возведение в степень  c помощью цикла
    public static long exp(long a, long b) {
        if (b < 0) {
            throw new RuntimeException("Метод не предназначен для расчета возведения числа "+ A +" в отрицательную степень "+B);
        }
        else if (a == 0) {
            return 0;
        } else {
            long result = 1;
            while (b > 0) {
                if (b % 2 == 0) {
                    a *= a;
                    b /= 2;
                } else {
                    result *= a;
                    b--;
                    if (result <= 0 ) {
                        throw new RuntimeException("Метод не предназначен для расчета возведения числа "+ A +" в степень "+B+". Результат выше диапазона long");
                    }
                }
            }
            return result;
        }
    }
}





