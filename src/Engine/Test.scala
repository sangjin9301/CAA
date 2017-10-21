package Engine

import Engine.CompareFreq_Location
import Engine.Preprocessor 

object Test {
  def main(args: Array[String]): Unit = 
  {
    var Score:Double = 0
    var processor = new Preprocessor
    processor.getData(false,"location", "a71c1917-7a94-4b2d-be2f-5be61c0415bb")
    processor.getData(true,"location", "7aa65059-7d66-4dbb-b1ec-d96e48bf4e9c")
    var rule = new CompareFreq_Location
    Score += rule.compare
    println("Score : "+Score)
    
  }
}