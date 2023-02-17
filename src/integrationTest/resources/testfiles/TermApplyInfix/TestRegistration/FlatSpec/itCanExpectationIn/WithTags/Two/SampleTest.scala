package dummy

import org.scalatest._

class SampleTest {

  it can "save" taggedAs(Tag("Tag1"), Tag("Tag2")) in {
    runProcess()
  }
}