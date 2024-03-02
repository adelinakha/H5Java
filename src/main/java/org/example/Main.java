package org.example;

import lombok.Data;
import java.util.Scanner;

// Класс клиента
@Data
public class Customer {
    private int id;
    private String name;
    private String address;
    private String email;
}

// Класс операции
@Data
public class Operation {
    private int id;
    private String type;
    private double amount;
    private String date;

    public Operation() {}

    public Operation(int id, String type, double amount, String date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public void print() {
        System.out.println("Operation: [id=" + id + ", type=" + type + ", amount=" + amount + ", date=" + date + "]");
    }
}

// Класс операции кэшбэка
@Data
public class CashbackOperation extends Operation {
    private int cashbackAmount;

    @Override
    public void print() {
        System.out.println("CashbackOperation: [id=" + getId() + ", type=" + getType() + ", amount=" + getAmount() + ", date=" + getDate() + ", cashbackAmount=" + cashbackAmount + "]");
    }
}

// Класс операции займа
@Data
public class LoanOperation extends Operation {
    private int loanId;

    @Override
    public void print() {
        System.out.println("LoanOperation: [id=" + getId() + ", type=" + getType() + ", amount=" + getAmount() + ", date=" + getDate() + ", loanId=" + loanId + "]");
    }
}

// Интерфейс для печати в консоль
public interface ConsolePrintable {
    void print();
}

// Главный класс
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer[] customers = new Customer[100];
        Operation[] operations = new Operation[100];
        int[][] statement = new int[100][100];
        int customerIdCounter = 0;
        int operationIdCounter = 0;

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Создать клиента");
            System.out.println("2. Создать операцию");
            System.out.println("3. Вывести операции клиента");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.println("Введите имя клиента:");
                String name = scanner.nextLine();
                System.out.println("Введите адрес клиента:");
                String address = scanner.nextLine();
                System.out.println("Введите email клиента:");
                String email = scanner.nextLine();

                Customer customer = new Customer();
                customer.setId(customerIdCounter++);
                customer.setName(name);
                customer.setAddress(address);
                customer.setEmail(email);

                customers[customer.getId()] = customer;
            } else if (choice == 2) {
                System.out.println("Введите тип операции:");
                String type = scanner.nextLine();
                System.out.println("Введите сумму операции:");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                System.out.println("Введите дату операции:");
                String date = scanner.nextLine();

                Operation operation = new Operation();
                operation.setId(operationIdCounter++);
                operation.setType(type);
                operation.setAmount(amount);
                operation.setDate(date);

                operations[operation.getId()] = operation;

                System.out.println("Введите id клиента для операции:");
                int customerId = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (customerId < customers.length && customers[customerId] != null) {
                    statement[customerId][operation.getId()] = operation.getId();
                } else {
                    System.out.println("Клиент не найден. Создать нового? (yes/no)");
                    String createNew = scanner.nextLine();
                    if (createNew.equalsIgnoreCase("yes")) {
                        System.out.println("Введите имя клиента:");
                        String name = scanner.nextLine();
                        System.out.println("Введите адрес клиента:");
                        String address = scanner.nextLine();
                        System.out.println("Введите email клиента:");
                        String email = scanner.nextLine();

                        Customer newCustomer = new Customer();
                        newCustomer.setId(customerIdCounter++);
                        newCustomer.setName(name);
                        newCustomer.setAddress(address);
                        newCustomer.setEmail(email);

                        customers[newCustomer.getId()] = newCustomer;
                        statement[newCustomer.getId()][operation.getId()] = operation.getId();
                    }
                }
            } else if (choice == 3) {
                System.out.println("Введите id клиента для вывода операций:");
                int customerId = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (customerId < customers.length && customers[customerId] != null) {
                    for (int operationId : statement[customerId]) {
                        if (operationId != 0) {
                            operations[operationId].print();
                        }
                    }
                } else {
                    System.out.println("Клиент не найден.");
                }
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }
}
