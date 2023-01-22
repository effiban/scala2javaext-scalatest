package dummy

import org.scalatest._

class SampleTest {

  "process" can "save" taggedAs(Tag("Tag1"), Tag("Tag2")) in {
    runProcess()
  }
}