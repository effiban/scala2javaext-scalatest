package io.github.effiban.scala2javaext.scalatest.transformers

trait JavaIdentifierNormalizer {

  def toMemberName(identifier: String): String
}

object JavaIdentifierNormalizer extends JavaIdentifierNormalizer {

  override def toMemberName(identifier: String): String = {
    identifier.split("\\s")
      .map(_.replaceAll("\\W", ""))
      .zipWithIndex
      .map { case (word, idx) => toLowerCamelCase(word, idx) }
      .mkString
  }

  private def toLowerCamelCase(word: String, idx: Int) = if (idx > 0) word.capitalize else word.toLowerCase
}
