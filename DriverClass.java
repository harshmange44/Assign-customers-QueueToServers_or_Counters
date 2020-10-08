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

import java.util.Random;

/**
 *
 * @author Harsh Mange
 */
public class DriverClass{

    public static void main(String[] args) {
        Random rd = new Random();
        int maxServers = 3;             //boundations for max servres
        int totalExperimentTime = 5;    //total amount of time(in minutes) for which the experiment will be proceed
        int maxServiceTime = 3;         //boundations for max service time
        int maxCustomersInAMin = 3;     //boundations for max customers in a min

        AssignQueueToServers assignQueueToServers = new AssignQueueToServers();

        for (int i = 1; i <= 7; i++) {    // Total Experiments: 7
            int servers = rd.nextInt(maxServers) + 1;  //generating random servers for each experiment from 1 to maxServers (inclusive) ...
            System.out.println("\n");
            System.out.println("==========Experiment: " + i + "=========Servers: " + servers + "===========");
            System.out.println("");
            assignQueueToServers.init(servers, maxServiceTime, totalExperimentTime, maxCustomersInAMin);
            assignQueueToServers.generateAndAssignCustomers();
        }
    }
}
