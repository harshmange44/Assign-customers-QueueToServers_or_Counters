/*
 * Copyright 2020 Harsh Mange.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Harsh Mange
 */
class AssignQueueToServers{

    int noOfServers;
    int experimentTime;
    int maxServiceTime;     // max service time for each customer in a minute
    int maxCustomers;       // max customers in a minute
    int longestWaitingTime = 0;
    int[] servers;
    int totalCustomers = 0;   // total cust. during experiment
    int maxGeneratedCustomers = 0;    // max generated cust in a min.
    double avgWaitingTime = 0;    // avg waiting time of all customers in the whole experiment
    Queue<Integer> queue = new Queue<>();

    Random rd = new Random();

    void init(int noOfServers, int maxServiceTime, int experimentTime, int maxCustomers) {
        this.noOfServers = noOfServers;
        this.maxServiceTime = maxServiceTime;
        this.experimentTime = experimentTime;
        this.maxCustomers = maxCustomers;
        servers = new int[noOfServers];
    }

    void generateAndAssignCustomers() {
        initServerTo0();    //init. server elements to 0 (calling method)
        for (int t = 1; t <= experimentTime; t++) {
            System.out.println("=========================================");
            System.out.println("Current Time: " + t + ((t == 1) ? " minute..." : " minutes..."));    // print current time in an experiment
            System.out.println("-----------------------------------------");
            generateCustomers();    //generating customers (calling method)

            assignCustomers();      // assigning customers (calling method)

            if (t == experimentTime) {  // print final experiment conclusion at t = experimentTime (before the programs ends)
//                System.out.println(String.format("%d %5d %5d %8.2f %5d %5d", noOfServers,longestWaitingTime,totalCustomers,(avgWaitingTime/totalCustomers),experimentTime,maxGeneratedCustomers));
                System.out.println("");
                System.out.println("===================================================================================================================================================================");
                System.out.println("No. of servers: " + noOfServers + " || Longest Waiting Time: " + longestWaitingTime + " || Total Customers: " + totalCustomers + " || avg. waiting time: " + String.format("%.2f", avgWaitingTime / totalCustomers) + "  || Max time of experiment: " + experimentTime + " || Max Customers in 1 min.: " + maxGeneratedCustomers);
                System.out.println("===================================================================================================================================================================");
            }
        }
    }

    private void generateCustomers() {      // this will generate customers in range of 0 to maxCustomer var. (inclusive) in 1 min.
        for (int i = 0; i < rd.nextInt(maxCustomers + 1); i++) {
            queue.queueEnqueue(rd.nextInt(maxServiceTime) + 1);      // Each customer will have service time between 1 to maxServiceTime (inclusive)
        }
        if (maxGeneratedCustomers < queue.queueLen()) {     // calc. & init. max generated customers in a minute during whole experiment
            maxGeneratedCustomers = queue.queueLen();
        }
        totalCustomers += queue.queueLen();     // counting total customers during experiment
        System.out.println(queue.queueLen() + " customers generated in a minute...");
        System.out.println("-----------------------------------------");
    }

    private int getServerWithMinWaitingTime() { // index of the server with minimum waiting time
        int min = servers[0];
        int index = 0;

        for (int i = 0; i < servers.length; i++) {
            if (servers[i] < min) {
                min = servers[i];
                index = i;
            }
        }
        return index;
    }

    private void initServerTo0() {  // initialize all server array elements to 0
        for (int i = 0; i < servers.length; i++) {
            servers[i] = 0;
        }
    }

    private void assignCustomers() {    //assign customers from queue to servers
        int noOfCust = queue.queueLen();
        for (int i = 0; i < noOfCust; i++) {
            avgWaitingTime += servers[getServerWithMinWaitingTime()];
            System.out.println("Current Waiting Time: " + servers[getServerWithMinWaitingTime()]);    //min. current waiting time(in server) of customer who just been assigning to server
            if (longestWaitingTime < servers[getServerWithMinWaitingTime()]) {
                longestWaitingTime = servers[getServerWithMinWaitingTime()]; // calc. & init. longest waiting time
            }

            servers[getServerWithMinWaitingTime()] += queue.queueDequeue().intValue();  //assigning customer to server
        }
        System.out.println("");
    }
}

class Queue<T> {    // generic queue class

    private ArrayList<T> queue = new ArrayList<T>();
    private int front = 0, rear = 0;

    int queueLen() { //queue length
        return queue.size();
    }

    void queueEnqueue(T data) {
        queue.add(rear, data);
        rear++;
    }

    T queueDequeue() {
        Object obj = null;
        if (front == rear) {
            //  System.out.printf("\nQueue is empty\n"); 
            return (T) obj;
        } else {
            obj = queue.get(0);
            queue.remove(0);
            rear--;
        }
        return (T) obj;
    }

    String queueDisplay() {
        int i;
        String s = "";

        if (front == rear) {
            System.out.printf("\nQueue is Empty\n");
            return "";
        }

        for (i = front; i < rear; i++) {
            s += queue.get(i).toString();
        }
        return s;
    }
}
