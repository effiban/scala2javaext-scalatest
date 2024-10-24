package testfiles.DefnDef.beforeEach

import org.scalatest.{BeforeAndAfterEach, Suite}

class SampleTest extends Suite with BeforeAndAfterEach {

  override protected def beforeEach(): Unit = {
    val x = 3
  }
}