package hw3;

import java.io.File;
import java.util.Scanner;

public class Tugrul_Demirozer_2020510027 {
    public static void main(String[] args) {
        // I prefer re-using variables instead of creating new ones
        // Therfore I only created one number of lines variable and scn variable
        int n = 15, p = 5, c = 5, numOfLines = 0;
        int[] demand, salary;
        Scanner scn;

        // Reads player demand File
        try {
            scn = new Scanner(new File("yearly_player_demand.txt"));
            scn.nextLine();
            while (scn.hasNextLine()) {
                scn.nextLine();
                numOfLines++;
            }
            demand = new int[numOfLines];
            scn = new Scanner(new File("yearly_player_demand.txt"));
            scn.nextLine();
            for (int i = 0; i < numOfLines; i++) {
                scn.nextInt();
                demand[i] = scn.nextInt();
            }
            // resetting number of lines to re use it
            numOfLines = 0;

        } catch (Exception e) {
            System.out.println("Yearly Player Demand File Not Found!");
            return;
        }
        // Reads player salary File
        try {
            scn = new Scanner(new File("players_salary.txt"));
            scn.nextLine();
            while (scn.hasNextLine()) {
                scn.nextLine();
                numOfLines++;
            }
            salary = new int[numOfLines];
            scn = new Scanner(new File("players_salary.txt"));
            scn.nextLine();
            for (int i = 0; i < numOfLines; i++) {
                scn.nextInt();
                salary[i] = scn.nextInt();
            }
        } catch (Exception e) {
            System.out.println("Players Salary File Not Found!");
            return;
        }
        scn.close();

        System.out.println("DP Result:" + DP(n, 1, p, c, demand, salary, 0, 0, Integer.MAX_VALUE));
    }

    public static int DP(int n, int currYear, int p, int c, int[] demand, int[] salary, int atHand, int cost,
            int subMinCost) {
        int diff, diffWOatHand;
        for (; currYear <= n; currYear++) {
            diff = demand[currYear - 1] - atHand - p; // different between demand and atHand and p
            diffWOatHand = demand[currYear - 1] - p; // we don't need all the atHand players therefore we calculate
                                                     // the difference without atHand
            atHand = Math.max(atHand - diffWOatHand, 0); // substraction of used atHand
            if (diff < 0) { // demand is less than p
                if (currYear + 1 > n) // if next year is the last year
                    return cost += salary[atHand - 1]; // add the salary of the remaining players if there is atHand
                                                       // player
                for (int i = 0; i <= Math.abs(diff); i++) {
                    subMinCost = Math.min(
                            DP(n, currYear + 1, p, c, demand, salary, i, i > 0 ? cost + salary[i - 1] : cost,
                                    subMinCost),
                            subMinCost); // calculate the minimum cost for each possible atHand player
                }
                return subMinCost; // return the minimum cost
            } else {
                cost += atHand > 0 ? Math.max(0, diff * c) + salary[atHand - 1]// calculate hiring cost and if there is
                                                                               // atHand player add the
                                                                               // salary of the atHand player
                        : Math.max(0, diff * c); //
            }
        }
        return cost;
    }
}