package io.github.effiban.scala2javaext.scalatest.normalizers

trait IdentifierNormalizer {

  def toClassName(identifier: String): String

  def toMemberName(identifier: String): String
}

object IdentifierNormalizer extends IdentifierNormalizer {

  override def toClassName(identifier: String): String = normalize(identifier, toUpperCamelCase)

  override def toMemberName(identifier: String): String = normalize(identifier, toLowerCamelCase)

  private def normalize(identifier: String, wordTransformer: (String, Int) => String) = {
    identifier.split("\\s")
      .map(_.replaceAll("\\W", ""))
      .zipWithIndex
      .map { case (word, idx) => wordTransformer(word, idx) }
      .mkString
  }

  private def toUpperCamelCase(word: String, idx: Int) = word.toLowerCase.capitalize

  private def toLowerCamelCase(word: String, idx: Int) = if (idx > 0) toUpperCamelCase(word, idx) else word.toLowerCase
}
