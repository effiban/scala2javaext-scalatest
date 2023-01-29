package io.github.effiban.scala2javaext.scalatest.matchers

import io.github.effiban.scala2java.test.utils.matchers.{ListMatcher, OptionMatcher, TreeMatcher}
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo
import org.scalatest.matchers.{MatchResult, Matcher}

import scala.meta.{Init, Term, Type}

class SpecInfoScalatestMatcher(expectedSpecInfo: SpecInfo) extends Matcher[SpecInfo] {

  override def apply(actualSpecInfo: SpecInfo): MatchResult = {

    val matches = namesMatch(actualSpecInfo) && actualSpecInfo.ignored == expectedSpecInfo.ignored

    MatchResult(matches,
      s"Actual context: $actualSpecInfo is NOT the same as expected spec info: $expectedSpecInfo",
      s"Actual context: $actualSpecInfo the same as expected spec info: $expectedSpecInfo"
    )
  }

  private def namesMatch(actualSpecInfo: SpecInfo) = actualSpecInfo.name.structure == expectedSpecInfo.name.structure
}

object SpecInfoScalatestMatcher {
  def equalSpecInfo(expectedSpecInfo: SpecInfo): Matcher[SpecInfo] = new SpecInfoScalatestMatcher(expectedSpecInfo)
}

