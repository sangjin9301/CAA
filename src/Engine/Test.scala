package Engine

 

object Test {
  def main(args: Array[String]): Unit = 
  {
    var processor = new Preprocessor
    processor.getData(false,"phoneCall", "e26edaae-ebb2-4c14-8575-f2335fc857ae")
    processor.getData(true,"phoneCall", "7aa65059-7d66-4dbb-b1ec-d96e48bf4e9c")
    processor.getData(false,"location", "a71c1917-7a94-4b2d-be2f-5be61c0415bb")
    processor.getData(true,"location", "7aa65059-7d66-4dbb-b1ec-d96e48bf4e9c")
    var rule01 = new CompareFreq_Location
    var rule02 = new CompareFreq_Call
    var result_01 = rule01.compare
    println("Score rule01 : "+result_01)
    var result_02 = rule02.compare
    println("Score rule02 + : "+result_02)
    
  }
}