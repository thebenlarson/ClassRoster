/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bl.interestcalculator;
import java.util.Scanner;
/**
 *
 * @author benth
 */
public class InterestCalculator {
    public void run() {
        Scanner myScanner = new Scanner(System.in);
        int years;
        float interestRate, initialPrincipal, currentBalance;
        System.out.println("What is the annual interest rate?");
        interestRate = myScanner.nextFloat();
        System.out.println("What is the principal?");
        initialPrincipal = myScanner.nextFloat();
        System.out.println("How many years?");
        years = myScanner.nextInt();
        
        currentBalance = initialPrincipal;
        for (int i = 1; i <= years; i++){
            float balance = currentBalance;
            for (int j = 0; j < 4; j++){
                currentBalance = currentBalance * ( 1 + interestRate /400);
            }
            System.out.println("Year: " + i + ", Starting Principal: " + balance + ", Interest Amount: " + (currentBalance - balance) + ", Ending Principal: " + currentBalance);
        }
    }
}
