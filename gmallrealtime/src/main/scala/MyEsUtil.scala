import java.util

import io.searchbox.client.{JestClient, JestClientFactory}
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.core.Index

object MyEsUtil {

  private var factory : JestClientFactory = null

  def build(): Unit = {
    factory = new JestClientFactory

    factory.setHttpClientConfig(
      new HttpClientConfig.Builder("")
        .multiThreaded(true)
        .maxTotalConnection(20)
        .connTimeout(10000)
        .readTimeout(1000)
        .build()
    )
  }

  def getClient:JestClient = {
    if(factory == null) build()
    factory.getObject
  }

  def putIndex: Unit = {
    val jest : JestClient = getClient

    val actorNameList : util.ArrayList[String] = new util.ArrayList[String]()

    actorNameList.add("zhangsan")

    val index :Index = new Index.Builder(Movie("100","天龙八部",actorNameList))
      .index("movie_index_5")
      .`type`("movie")
      .id("1")
      .build()

    jest.execute(index)

    jest.close()
  }

  def main(args: Array[String]): Unit = {
    putIndex
  }


}
