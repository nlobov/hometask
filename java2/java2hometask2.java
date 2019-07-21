package java2;

import java.util.Arrays;
/*
* Домашнее задание курса java2 по уроку 2 "Исключения"
* dated Jul 21, 2019
*/

public class java2hometask2 {

    private final static int ROWS = 4;
    private final static int COLOMNS = 4;

    private final static String S = "1 3 1 2\n2 3 2 2\n5 6 7 1\n3 3 1 0";
//    входные данные с ошибками
//    private final static String S = "1 3 1 2\n2 3 2 2\n5 6 7 1";
//    private final static String S = "1 3 1 2\n2 3 2 2\n5 6 7 1\n3 3 1 ";
//    private final static String S = "1 3 1 2\n2 3 5 2\n5 6 7 1\n3 3 1 a";

    public static void main(String[] args) {
        System.out.println("Входные данные:\n"+S);
        try {
            String [][] matrix = string2Matrix(S);
            System.out.println(Arrays.deepToString(matrix));
            System.out.println("Сумма: "+calcMatrix(matrix));
        }
        catch (MyWrongFormat e){
            System.out.println("произошел MyWrongFormat: "+e.getMessage());
        }
        catch (MyIncorrectCountRows e){
            System.out.println("произошел MyIncorrectCountRows: "+e.getMessage());
        }
        catch (MyIncorrectCountColumns e){
            System.out.println("произошел MyIncorrectCountColumns: "+e.getMessage());
        }
    }

    private static String [][] string2Matrix (String value){
        String [] rows = value.split("\n");

        if (rows.length != ROWS){throw new MyIncorrectCountRows("Некорректное количество строк \n"+value);}
        String [][] matrix = new String[ROWS][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i]=rows[i].split(" ");
            if (matrix[i].length != COLOMNS){throw new MyIncorrectCountColumns("Некорректное количество столбцов \n"+value);}
        }
        return matrix;
    }
    private static int calcMatrix(String[][] matrix){
        int result =0;
        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j <matrix[i].length ; j++) {
                if (!isInt(matrix[i][j])) {throw new MyWrongFormat("Неверный формат символа "+matrix[i][j]);}
                result +=Integer.parseInt(matrix[i][j]);
            }
        }
        return result/2;
    }

    private static boolean isInt(String s) throws NumberFormatException{
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}

class MyIncorrectCountRows extends RuntimeException{

    MyIncorrectCountRows(String message) {
        super(message);
    }
}
class MyIncorrectCountColumns extends RuntimeException{

    MyIncorrectCountColumns(String message) {
        super(message);
    }
}
class MyWrongFormat extends NumberFormatException {
    MyWrongFormat(String message) {
        super(message);
    }
}
