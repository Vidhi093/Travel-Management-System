class spider{

    public static void recur(int s,int sum){
    
        if(s>20){
            System.out.println("Sum of odd numbers: " + sum);
            return;
        }
                if(s%2!=0){
                System.out.println(s);
                sum= sum+s;
            }
            
        
        recur(s+1,sum);
            
    }
    public static void main(String[] args) {
        recur(1,0);
        }
        
    }
    /*public static void main(String[] args) {
        int start = 1;
        int end = 20;
        
        System.out.println("Odd numbers between " + start + " and " + end + ":");
        printOddNumbersAndFindSum(start, end, 0);
    }

    public static void printOddNumbersAndFindSum(int start, int end, int sum) {
        if (start > end) {
            System.out.println("Sum of odd numbers: " + sum);
            return;
        }

        if (start % 2 != 0) {
            System.out.print(start + " ");
            sum += start;
        }

        printOddNumbersAndFindSum(start + 1, end, sum);
    }
}*/