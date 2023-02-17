package dummy

import org.scalatest._

class SampleTest {

  test("check", Tag("myTag")) {
    performTheCheck()
  }
}