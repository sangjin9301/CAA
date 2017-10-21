package Engine

import java.util.Calendar


class Location() {
  private var latitude:Double = 0
  private var longitute:Double = 0
  private var timestamp:Double = 0
  private var dayOfWeek:Int = 0
  
  
  def setTimestamp(timestamp:Double):Unit=
  {
    this.timestamp = timestamp
    
    var rs = new java.util.Date(timestamp.toLong)
    var cal = Calendar.getInstance();
    cal.setTime(rs);
    setDayOfWeek(cal.get(java.util.Calendar.DAY_OF_WEEK));

  }
  def setLatitude(latitude:Double):Unit=
  {
    this.latitude = latitude
  }
  def setLongitute(longitute:Double):Unit=
  {
    this.longitute = longitute
  }
  def setDayOfWeek(dow:Int):Unit=
  {
    this.dayOfWeek = dow
  }
  
  def getTimestamp:Double=
  {
    return this.timestamp
  }
  def getLatitude:Double=
  {
    return this.latitude
  }
  def getLongitute:Double=
  {
    return this.longitute
  }
  def getDayOfWeek:Int=
  {
    return this.dayOfWeek
  }
  
}