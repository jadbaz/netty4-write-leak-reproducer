A reproducer for a bug with Netty 4.

To reproduce this bug yourself:
1. Clone this project
2. chmod +x gradlew
3. ./gradlew build
4. java -jar build/libs/<latest>.jar

Program options available:
1. -Dtest.batch: The number of messages to send before sleep
2. -Dtest.sleep: The sleep duration in millis after sending 1 batch (test.batch)
3. -Dtest.length: The length of a single message
4. -Dtest.random: Generate random strings or not (true/false)
5. -Dtest.log_time: The log interval in millis
6. -Dtest.port: The port to run the server