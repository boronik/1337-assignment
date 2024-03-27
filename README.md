# 1337-assignment

Design decisions:
1. Divide into logically independent units that can be tested individually
2. Recursion and parallel execution to achieve reasonable performance
3. Use of existing open source libraries, which can shorten development time

Tools:
1. java 17
2. maven (tested with version 3.9.4)
3. IDE (Optional, I used Eclipse)

Here's how to compile and run:
1. Clone the allocation repository to your local computer
2. Open terminal in the folder with pom.xml in it
3. In the terminal window run: [mvn clean compile exec:java]
      - maven will compile and run the program
      - at the top of the terminal window you will find a line OUTPUT:
