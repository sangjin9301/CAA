package Engine

import java.util.Calendar

class CDR {
  //시간
  //대상
  //통화시간
  //요일
  private var timestamp:Double = 0
  private var target:String = ""
  private var duration:Double = 0
  private var dayOfWeek:Int = 0
  
  def setTimestamp(ts:Double):Unit = 
  {
    this.timestamp = ts
    
    var rs = new java.util.Date(ts.toLong)
    var cal = Calendar.getInstance();
    cal.setTime(rs);
    setDayOfWeek(cal.get(java.util.Calendar.DAY_OF_WEEK));
  }
  
  def setTarget(target:String):Unit = 
  {
    this.target = target
  }
  
  def setDuration(du:Double):Unit = 
  {
    this.duration = du
  }
  
  def setDayOfWeek(dw:Int):Unit=
  {
    this.dayOfWeek = dw
  }
  
  def getTimestamp:Double = 
  {
    return this.timestamp
  }
  
  def getTarget:String =
  {
    return this.target
  }
  
  def getDuration:Double =
  {
    return this.duration
  }
  
  def getDayOfWeek:Int=
  {
    return this.dayOfWeek
  }
}