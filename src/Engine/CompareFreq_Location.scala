package Engine

import java.util.HashMap
import scala.collection.mutable.ArrayBuffer
import org.apache.commons.math3.distribution.NormalDistribution
import org.apache.commons.math3.distribution.TDistribution

class CompareFreq_Location {

  def compare: Double =
    {
      if(Store.LocationArray_Base.isEmpty){println("Base data is Empty.");return 0}
      if(Store.LocationArray_Req.isEmpty){println("Request data is Empty.");return 0}
      var baseMap = getFrequency(Store.LocationArray_Base)
      var reqMap = getFrequency(Store.LocationArray_Req)
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

          oneDay_req.forEach((key: Double, value: Double) =>
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

  def getDataByDOW(arr: Array[Location], DOW: Int): Array[Location] =
    {
      var dowArray = new ArrayBuffer[Location]
      var i: Int = 0
      while (i < arr.length) 
      {
        if (arr(i).getDayOfWeek.equals(DOW)) dowArray += arr(i)
        i += 1
      }

      return dowArray.toArray
    }

  def getFrequency(arr: Array[Location]): HashMap[Int, HashMap[Double, Double]] =
    {
      var dowMap = new HashMap[Int, HashMap[Double, Double]]
      var i: Int = 1
      var j: Int = 0
      while (i < 8) 
      {
        var freqMap = new HashMap[Double, Double]
        var dailyLocation = getDataByDOW(arr, i)
        if (dailyLocation.isEmpty){} //println("i has null data")
        while (j < dailyLocation.length) 
        {
          var key = dailyLocation(j).getLatitude
          if (freqMap.containsKey(key)) 
          {
            freqMap.put(key, freqMap.get(key) + 1)
          } else freqMap.put(key, 1)

          j += 1
        }
        var whole: Double = 0
        freqMap.forEach((key: Double, value: Double) => whole += value)
        freqMap.forEach((key: Double, value: Double) => freqMap.put(key, value / whole * 100))
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