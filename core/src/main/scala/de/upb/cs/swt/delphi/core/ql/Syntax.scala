// Copyright (C) 2018 The Delphi Team.
// See the LICENCE file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.upb.cs.swt.delphi.core.ql

import org.parboiled2.{CharPredicate, Parser, ParserInput, Rule1}

/**
  * The syntax definition and parser for the Delphi QL.
  *
  * @author Lisa Nguyen Quang Do
  * @author Ben Hermann
  *
  */
class Syntax(val input : ParserInput) extends Parser {

  def QueryRule = rule {
    CombinatorialRule ~ EOI
  }

  // Combinatorial rules.
  def CombinatorialRule : Rule1[CombinatorialExpr] = rule {
    OrOrElseRule | NotRule
  }
  def OrOrElseRule = rule {
    AndOrElseRule ~ zeroOrMore(Whitespace ~ "||" ~ Whitespace ~ AndOrElseRule ~> OrExpr)
  }
  def AndOrElseRule = rule {
    XorOrElseRule ~ zeroOrMore(Whitespace ~ "&&" ~ Whitespace ~ XorOrElseRule ~> AndExpr)
  }
  def XorOrElseRule = rule {
    Factor ~ zeroOrMore(Whitespace ~ "%%" ~ Whitespace ~ Factor ~> XorExpr)
  }

  // Handling parentheses.
  def Factor : Rule1[CombinatorialExpr] = rule {
    Parentheses | SingularConditionRule | NotRule
  }
  def Parentheses = rule { '(' ~ CombinatorialRule ~ ')' }
  def NotRule = rule { '!' ~ Factor ~> NotExpr }

  // Singular conditions.
  def SingularConditionRule = rule {
    EqualRule | NotEqualRule | GreaterThanRule | GreaterOrEqual |
      LessThan | LessOrEqual | Like | IsTrue
  }
  def EqualRule = rule { FieldReferenceRule ~ Whitespace ~ "=" ~ Whitespace ~ Literal ~> EqualExpr }
  def NotEqualRule = rule { FieldReferenceRule ~ Whitespace ~ "!=" ~ Whitespace ~ Literal ~> NotEqualExpr }
  def GreaterThanRule = rule { FieldReferenceRule ~ Whitespace ~ ">" ~ Whitespace ~ IntegerLiteral ~> GreaterThanExpr }
  def GreaterOrEqual = rule { FieldReferenceRule ~ Whitespace ~ ">=" ~ Whitespace ~ IntegerLiteral ~> GreaterOrEqualExpr }
  def LessThan = rule { FieldReferenceRule ~ Whitespace ~ "<" ~ Whitespace ~ IntegerLiteral ~> LessThanExpr }
  def LessOrEqual = rule { FieldReferenceRule ~ Whitespace ~ "<=" ~ Whitespace ~ IntegerLiteral ~> LessOrEqualExpr }
  def Like = rule { FieldReferenceRule ~ Whitespace ~ "%" ~ Whitespace ~ StringLiteral ~> LikeExpr }
  def IsTrue = rule { FieldReferenceRule ~> IsTrueExpr }

  // Literals
  def FieldReferenceRule = rule { "[" ~ capture(oneOrMore(CharPredicate.AlphaNum ++ '-' ++ ' ' ++  '_' ++ '(' ++ ':' ++')')) ~ "]"  ~> FieldReference }

  def IntegerLiteral = rule { capture(oneOrMore(CharPredicate.Digit)) }
  def StringLiteral = rule { '"' ~ capture(oneOrMore(CharPredicate.AlphaNum)) ~ '"'}

  def Literal = rule { (IntegerLiteral | StringLiteral ) ~> (_.toString) }

  def Whitespace = rule {
    zeroOrMore(anyOf(" \n \r"))
  }

  def OneOrMoreWhitespace = rule {
    oneOrMore(anyOf(" \n \r"))
  }
}


