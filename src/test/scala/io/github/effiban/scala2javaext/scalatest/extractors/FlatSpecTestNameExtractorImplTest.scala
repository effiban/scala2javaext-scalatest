package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

class FlatSpecTestNameExtractorImplTest extends UnitTestSuite {

  private val ValidScenarios = Table(
    ("Name Clause", "Name"),
    (q""""aaa" can "bbb"""", "aaa can bbb"),
    (q""""aaa" must "bbb"""", "aaa must bbb"),
    (q""""aaa" should "bbb"""", "aaa should bbb"),
    (q"""it can "bbb"""", "it can bbb"),
    (q"""it must "bbb"""", "it must bbb"),
    (q"""it should "bbb"""", "it should bbb"),
  )

  private val InvalidScenarios = Table(
    "Name Clause",
    q""""aaa" will "bbb"""",
    q""""aaa" likes "bbb"""",
    q""""aaa" after "bbb"""",
    q"""it may "bbb"""",
    q"""it is "bbb"""",
    q"""bla bla "bla"""",
    q"x + 3",
  )

  forAll(ValidScenarios) { case (nameClause: Term.ApplyInfix, name: String) =>
    test(s"""For name clause: <$nameClause> the name: "$name" should be extracted""") {
      FlatSpecTestNameExtractor.extract(nameClause).value.structure shouldBe Lit.String(name).structure
    }
  }

  forAll(InvalidScenarios) { nameClause: Term.ApplyInfix =>
    test(s"Should fail to extract a name for the clause: <$nameClause>") {
      FlatSpecTestNameExtractor.extract(nameClause) shouldBe None
    }
  }
}
