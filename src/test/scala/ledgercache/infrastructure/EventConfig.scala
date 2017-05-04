package ledgercache.infrastructure

import com.amazonaws.regions.Regions

class EventConfig(val eventType : String) {
  var region = Regions.US_WEST_2
}
