package dummy

import org.scalatest._

class SampleTest extends BeforeAndAfterEach {

  override protected def beforeEach(): Unit = {
    prepare()
  }
}