import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            System.out.println("Welcome to the Ping Scanner! :) ");
            System.out.print("Please enter a subnet without the last digit(192.168.86.):  ");
            Scanner keyboard = new Scanner(System.in);
            String sIP = keyboard.nextLine();
            MultiThreadedScanner.ping_scan_range(sIP);
        }
    }
