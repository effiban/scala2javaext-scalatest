package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTypeClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.any

import scala.meta.{Init, Type, XtensionQuasiquoteInit, XtensionQuasiquoteType}

class ClassNameImplTransformerTest extends UnitTestSuite {

  private val scalatestTypeClassifier = mock[ScalatestTypeClassifier]
  private val transformer = new ClassNameTransformerImpl(scalatestTypeClassifier)

  private val Scenarios = Table(
    ("ScalaTest FileName", "Class Inits", "Expected JUnit FileName"),
    ("MySpec", List(init"AnyFunSpec"), "MyTest"),
    ("MySpec", List(init"BlaBla"), "MySpec"),
    ("MySpec", Nil, "MySpec"),
    ("MySuite", List(init"AnyFunSpec"), "MyTest"),
    ("MySuite", List(init"BlaBla"), "MySuite"),
    ("MySuite", Nil, "MySuite"),
    ("MyTest", List(init"AnyFunSpec"), "MyTest"),
    ("MyTest", List(init"BlaBla"), "MyTest"),
    ("MyTest", Nil, "MyTest"),
    ("MyBla", List(init"AnyFunSpec"), "MyBlaTest"),
    ("MyBla", List(init"BlaBla"), "MyBla"),
    ("MyBla", Nil, "MyBla")
  )

  forAll(Scenarios) { case (fileName: String, classInits: List[Init], expectedFileName: String) =>
    test(s"The filename '$fileName' ${if (classInits.nonEmpty) s"with a class extending $classInits" else ""} should be transformed to '$expectedFileName'") {
      when(scalatestTypeClassifier.isTestSuperclass(any[Type])).thenAnswer((tpe: Type) => tpe match {
        case t"AnyFunSpec" => true
        case _ => false
      })
      transformer.transform(fileName, classInits) shouldBe expectedFileName
    }
  }
}
