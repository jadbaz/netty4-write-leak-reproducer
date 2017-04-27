A reproducer for a bug with Netty 4.

To reproduce this bug yourself:
Clone this project
./gradlew build
java -jar build/libs/<latest>.jar

Program options available:
1. -Dtest.batch: The number of messages to send before sleep
2. -Dtest.sleep: The sleep duration in millis after sending 1 batch (test.batch)
2. -Dtest.length: The length of a single message
3. -Dtest.log_time: The log interval in millis
4. -Dtest.port: The port to run the server