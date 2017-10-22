package Engine

import org.apache.commons.math3.distribution.TDistribution
import org.apache.commons.math3.distribution.NormalDistribution
import scala.collection.mutable.ArrayBuffer
import java.util.HashMap

class CompareFreq_Call {
   def compare: Double =
    {
      if(Store.CallArray_Base.isEmpty){println("Base Call data is Empty.");return 0}
      if(Store.CallArray_Req.isEmpty){println("Request Call data is Empty.");return 0}
      var baseMap = getFrequency(Store.CallArray_Base)
      var reqMap = getFrequency(Store.CallArray_Req)
      var i: Int = 1
      var returnArray = new ArrayBuffer[ArrayBuffer[Double]]
      while (i < 8) 
      {
        if (reqMap.get(i).isEmpty()){} //println(i + "   req data is null")//1=일요일, 7=토요일 
        else 
        {
          var resultArray = new ArrayBuffer[Double]
          var oneDay_base = baseMap.get(i)
          var oneDay_req = reqMap.get(i)

          oneDay_req.forEach((key: String, value: Double) =>
            if (oneDay_base.containsKey(key)) 
            {
              var result = oneDay_req.get(key) - oneDay_base.get(key)
              resultArray += result
            } else 
            {
              oneDay_base.put(key, 0)
              var result = oneDay_req.get(key) - oneDay_base.get(key)
              resultArray += result
            })
          returnArray += resultArray
        }
        i+=1
      }
      var tScore = tTest(returnArray)
      var zScore = zTest(returnArray)
      var Score = Array.concat(tScore.toArray, zScore.toArray)
      var sum:Double = 0
      Score.foreach((x:Double) => sum += x)
      return sum/Score.size
    }

  def getDataByDOW(arr: Array[CDR], DOW: Int): Array[CDR] =
    {
      var dowArray = new ArrayBuffer[CDR]
      var i: Int = 0
      while (i < arr.length) 
      {
        if (arr(i).getDayOfWeek.equals(DOW)) dowArray += arr(i)
        i += 1
      }

      return dowArray.toArray
    }

  def getFrequency(arr: Array[CDR]): HashMap[Int, HashMap[String, Double]] =
    {
      var dowMap = new HashMap[Int, HashMap[String, Double]]
      var i: Int = 1
      var j: Int = 0
      while (i < 8) 
      {
        var freqMap = new HashMap[String, Double]
        var dailyCall = getDataByDOW(arr, i)
        if (dailyCall.isEmpty){} //println("i has null data")
        while (j < dailyCall.length) 
        {
          var key = dailyCall(j).getTarget
          if (freqMap.containsKey(key)) 
          {
            freqMap.put(key, freqMap.get(key) + 1)
          } else freqMap.put(key, 1)

          j += 1
        }
        var whole: Double = 0
        freqMap.forEach((key: String, value: Double) => whole += value)
        freqMap.forEach((key: String, value: Double) => freqMap.put(key, value / whole * 100))
        dowMap.put(i, freqMap)
        i += 1
      }
      return dowMap
    }
  
  def tTest(arr : ArrayBuffer[ArrayBuffer[Double]]):ArrayBuffer[Double]=
  {
    var i:Int = 0
    var score = new ArrayBuffer[Double]
    while(i<arr.size)
    {
      var tDist = new TDistribution(arr(i).size)
      var j:Int=0
      var oneDayArray = arr(i)
      var sum:Double = 0
      var mean:Double = 0
      var s:Double = 0
      var n = oneDayArray.size
      while(j<n)
      {
        sum += oneDayArray(j)
        j += 1
      }
      mean = sum/n
      if(n<30 && n>=2) score += (1-(0.5 - tDist.cumulativeProbability(mean)).abs)
      //t를 정규분포에 대입해서 0.5와의 차이를 1에서 뺌 = 점수 
      i += 1
    }
    return score
  }
  
  def zTest(arr : ArrayBuffer[ArrayBuffer[Double]]):ArrayBuffer[Double]=
  {
    var norm = new NormalDistribution // mean 0
    var i:Int = 0
    var score = new ArrayBuffer[Double]
    while(i<arr.size)
    {
      var j:Int=0
      var oneDayArray = arr(i)
      var sum:Double = 0
      var mean:Double = 0
      var s:Double = 0
      var n = oneDayArray.size
      while(j<n)
      {
        sum += oneDayArray(j)
        j += 1
      }
      mean = sum/n
      if(n>=30) score += (1-(0.5 - norm.cumulativeProbability(mean)).abs)
      //var z = (mean-0)/(s/Math.sqrt(n))// n의 제곱근은 표준오차 
      //z를 정규분포에 대입해서 0.5와의 차이를 1에서 뺌 = 점수 
      i += 1
    }
    return score
  }
}