import scala.util.Random
import org.joda.time.format.DateTimeFormat

object EventProducerApp extends App {

  /*
  field           distibution type requirement              data requirement
  product name    uniform
  product price   gaussian
  purchase date    time - gaussian  date - uniform         1 week range
  product category    uniform
  client IP address  uniform                               IPv4 random adresses
*/

  if (args.length == 0) throw new Exception("Missing arg - No batch size defined")

  val Products = Map(0 -> ("iPhone","Phone"),
                      1 -> ("iPad","Tablet"),
                      2 -> ("GalaxyS","Phone"),
                      3 -> ("GalaxyS2","Phone"),
                      4 -> ("GalaxyTab","Tablet"),
                      5 -> ("GalaxyTab2","Tablet")
                  )

  val unixStartDate = 1546819200L // Jan 7th 2019 00:00:00
  val secsInDay = 86400L
  val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC()

  def getRow() = {
    val product = Products(Random.nextInt(Products.keys.max+1))
    val price = (Random.nextGaussian()*30).floor+300
    val IPAddress = s"${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}.${Random.nextInt(256)}"
    val dateTime = unixStartDate +
                   Random.nextInt(7)*secsInDay +
                   ((Random.nextGaussian()+1)*secsInDay).floor.toLong - 1

    s"${product._1},${price},${dateTimeFormat.print(dateTime*1000)},${product._2},${IPAddress}"
  }

  0 to args(0).toInt foreach(_ => println(getRow()))

}
