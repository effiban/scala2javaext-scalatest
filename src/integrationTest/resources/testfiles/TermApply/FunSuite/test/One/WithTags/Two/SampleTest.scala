package dummy

import org.scalatest._

class SampleTest {

  test("check", Tag("tag1"), Tag("tag2")) {
    performTheCheck()
  }
}