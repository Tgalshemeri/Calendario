package com.treaf.calendario

 class Events(a: ArrayList<String>, b: ArrayList<String> , c:ArrayList<String>) {
      var getDate: ArrayList<String> = a
     var getEvent: ArrayList<String> = b
     var getTime:ArrayList<String> = c

     fun getBoth(
         arrayList: ArrayList<String>,
         dateList: ArrayList<String>,
         timeList: ArrayList<String>
     ): Events = Events(dateList, arrayList , timeList)
 }