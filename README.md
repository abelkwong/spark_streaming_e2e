# End-to-end streaming with Apache Spark
This repository contains a Scala implementation of an end-to-end streaming pipeline using Apache Spark. The pipeline ingests data from a Kafka topic, processes it with Spark Streaming, and then writes the results back to Kafka.

The main entry point of the pipeline is the end_to_end_streaming.scala file, which contains the following steps:

1. Set up the Spark and Kafka configurations
2. Create a Spark Streaming context with a batch interval of 5 seconds
3. Define the Kafka topic to read from and write to
4. Create a DStream that reads from the input Kafka topic
5. Parse the incoming data (assuming it is in CSV format)
6. Perform some basic data processing (e.g., filtering, grouping, aggregating)
7. Write the processed data back to the output Kafka topic

The code is well-commented, and there are some additional notes in the `NOTES.md` file. Feel free to modify the code as needed for your own use case.

## Requirements
- Apache Spark 2.3.0 or later
- Kafka 2.1.0 or later

## Usage
1. Clone this repository to your local machine.
2. Modify the Spark and Kafka configurations in end_to_end_streaming.scala to match your environment.
3. Run sbt package to compile the code and generate a JAR file.
4. Submit the JAR file to your Spark cluster using spark-submit.
5. Watch the output Kafka topic for the results of the streaming pipeline.

## Credits
This code was created by Abel Kwong and is distributed under the MIT license.
