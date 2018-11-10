package ru.spb.hse.calculator

import org.antlr.v4.runtime._

object Calculator {
  private val EXIT = "exit"
  private val evaluator = new Evaluator()
  private val prefix = "> "

  def main(args: Array[String]): Unit = {
    println("Arithmetic and logic evaluator:")
    var input = readNextInput

    while (input.toLowerCase() != EXIT) {
      try {
        println(eval(input))
      } catch {
        case _: Throwable => println("Incorrect expression.")
      }

      input = readNextInput
    }
  }

  def eval(expr: String): Int = {
    val lexer = new ExpressionLexer(CharStreams.fromString(expr))
    val parser = new ExpressionParser(new CommonTokenStream(lexer))
    parser.calc().accept(evaluator)
  }

  private def readNextInput : String = {
    print(prefix)
    scala.io.StdIn.readLine()
  }
}
