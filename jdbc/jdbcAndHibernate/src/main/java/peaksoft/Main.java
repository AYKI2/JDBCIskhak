package peaksoft;

import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        boolean isTrue = true;
        while(isTrue){
            System.out.println("""
                    1 - Create Table,
                    2 - Drop Table,
                    3 - Save User,
                    4 - Remove User By Id,
                    5 - Show All Users,
                    6 - Truncate,
                    7 - Exit;
                    """);
            int choose = new Scanner(System.in).nextInt();
            switch (choose){
                case 1 -> userService.createUsersTable();
                case 2 -> userService.dropUsersTable();
                case 3 -> {
                    System.out.println("Enter name:");
                    String name = new Scanner(System.in).nextLine();
                    System.out.println("Enter lastName:");
                    String lastName = new Scanner(System.in).nextLine();
                    System.out.println("Enter age:");
                    byte age = new Scanner(System.in).nextByte();
                    userService.saveUser(name,lastName,age);
                }
                case 4 -> {
                    System.out.println("Enter Id: ");
                    userService.removeUserById(new Scanner(System.in).nextLong());
                }
                case 5 -> System.out.println(userService.getAllUsers());
                case 6 -> userService.cleanUsersTable();
                case 7 -> isTrue = false;
            }
        }
        // реализуйте алгоритм здесь
    }
}
