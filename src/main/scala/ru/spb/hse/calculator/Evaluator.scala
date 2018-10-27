package ru.spb.hse.calculator

class Evaluator extends ExpressionBaseVisitor[Int] {

  override def visitCalc(ctx: ExpressionParser.CalcContext): Int =
    ctx.expr.accept(this)

  override def visitExpression(ctx: ExpressionParser.ExpressionContext): Int = getType(ctx) match {
    case 0 => ctx.literal.getText.toInt
    case 1 => ctx.exp.accept(this)
    case _ => operation(ctx.op.getText)(ctx.left.accept(this), ctx.right.accept(this))
  }

  def operation(op: String): (Int, Int) => Int = op match {
    case "+" => _ + _
    case "-" => _ - _
    case "*" => _ * _
    case "/" => _ / _
    case "%" => _ % _
    case "<" => (x, y) => if (x < y) 1 else 0
    case ">" => (x, y) => if (x > y) 1 else 0
    case "<=" => (x, y) => if (x <= y) 1 else 0
    case ">=" => (x, y) => if (x >= y) 1 else 0
    case "==" => (x, y) => if (x == y) 1 else 0
    case "!=" => (x, y) => if (x != y) 1 else 0
    case "&&" => (x, y) => if (x != 0 && y != 0) 1 else 0
    case "||" => (x, y) => if (x != 0 || y != 0) 1 else 0
  }

  def getType(ctx: ExpressionParser.ExpressionContext): Int = {
    if (ctx.literal != null)
      0
    else if (ctx.exp != null)
      1
    else
      2
  }
}
