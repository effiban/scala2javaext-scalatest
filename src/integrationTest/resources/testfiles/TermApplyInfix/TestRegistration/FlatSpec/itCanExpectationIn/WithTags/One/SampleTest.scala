package dummy

import org.scalatest._

class SampleTest {

  it can "save" taggedAs(Tag("MyTag")) in {
    runProcess()
  }
}