import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka010._
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies._
import org.apache.spark.streaming.kafka010.ConsumerStrategies._


import com.datastax.spark.connector.SomeColumns
import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.streaming._

val sparkConf = new SparkConf()
  .setAppName("KafkaSparkStreaming")
  .set("spark.cassandra.connection.host", "127.0.0.1")

val ssc = new StreamingContext(sparkConf, Seconds(20))

val kafkaParams = Map[String, Object](
  "bootstrap.servers" -> "localhost:9092",
  "key.deserializer" -> classOf[StringDeserializer],
  "value.deserializer" -> classOf[StringDeserializer],
  "group.id" -> "sparkgroup",
  "auto.offset.reset" -> "latest",
  "enable.auto.commit" -> (false: java.lang.Boolean)
)

val topics = Array("mytopic")

val lines = KafkaUtils.createDirectStream[String, String](
  ssc,
  LocationStrategies.PreferConsistent,
  ConsumerStrategies.Subscribe[String, String](Seq("mytopic"), kafkaParams)
).map(record => record.value())

lines.print()

lines.map(line => {
  val arr = line.split(",")
  (arr(0), arr(1), arr(2), arr(3), arr(4))
}).saveToCassandra(
  "sparkdata",
  "cust_data",
  SomeColumns("fname", "lname", "url", "product", "cnt")
)

ssc.start()
ssc.awaitTermination()