package Engine

import scala.io.Source
import org.json.JSONObject
import Engine.Store
import Engine.Location
import org.json.JSONObject



class Preprocessor {
  private var url = "https://dvikqteix2.execute-api.ap-northeast-2.amazonaws.com/prod/finopass/logs?"
  private var userKey = ""
  
  def setUser(userKey : String) : Unit = 
  {
    this.userKey = userKey
  }
  
  //location data
  def getData(isbase:Boolean, dataType : String, userKey : String) : Unit = 
  {
    if(dataType.equals("location"))
    {
      if(isbase)
      {
        Store.LocationArray_Base = getLocation(userKey)
        println("Type : Base,       UserKey : "+userKey)
      }
      else
      {
        Store.LocationArray_Req = getLocation(userKey)
        println("Type : Request,    UserKey : "+userKey)
      }
    }
//    else if(dataType.equals("phoneCall"))
//    {
//      
//    }
  }
  
  def getLocation(userKey:String):Array[Location]=
  {
    setUser(userKey)
    var query = url + "types=location" + "&userKey=" + this.userKey 
    var html = Source.fromURL(query)
    var s = "{location:"+html.mkString+"}"
    var jObject = new JSONObject(s);
    var JSArray = jObject.getJSONArray("location");
    
    var i:Int = 0
    var datas:Array[Location] = new Array[Location](JSArray.length)
    
    while(i<JSArray.length())
    {
      var location = new Location()
      var loc = JSArray.getJSONObject(i);
      var locData = loc.getJSONObject("data");
      var info = new JSONObject(locData.toString)
      
      location.setTimestamp(info.get("timestamp").toString().toDouble)
      location.setLatitude(info.get("latitude").toString().toDouble)
      location.setLongitute(info.get("longitute").toString().toDouble)
      datas(i) = location
      i += 1
    }
    return datas
  }
  
//  def getPhoneCall(userKey:String):Array[CDR]=
//  {
//    
//  }
}