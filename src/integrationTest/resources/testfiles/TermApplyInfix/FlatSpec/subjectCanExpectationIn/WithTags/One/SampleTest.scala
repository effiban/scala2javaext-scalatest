package dummy

import org.scalatest._

class SampleTest {

  "process" can "save" taggedAs(Tag("MyTag")) in {
    runProcess()
  }
}