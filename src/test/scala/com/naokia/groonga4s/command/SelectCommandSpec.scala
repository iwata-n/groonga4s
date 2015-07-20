package com.naokia.groonga4s.command

import org.specs2.mutable.Specification

/**
  * Created by naoki on 15/04/29.
  */
class SelectCommandSpec extends Specification{
   "SelectCommand generates path and query" >> {
     "with table name only" >>  {
       val command = new SelectCommand(SelectParameters(table="Entries"))
       command.stringify() must equalTo("/d/select.json?table=Entries")
     }
     "with query" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", query=Some("name:John")))
       command.stringify() must equalTo("/d/select.json?table=Entries&query=name%3AJohn")
     }
     "with matchColumns" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", query=Some("name:John"), matchColumns=Seq("name")))
       command.stringify() must equalTo("/d/select.json?table=Entries&match_columns=name&query=name%3AJohn")
     }
     "with filter" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", filter=Some("n_likes >= 5")))
       command.stringify() must equalTo("/d/select.json?table=Entries&filter=n_likes+%3E%3D+5")
     }
     "with sortby" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", sortby=Seq("_id")))
       command.stringify() must equalTo("/d/select.json?table=Entries&sortby=_id")
     }
     "with scorer" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", scorer=Some("_score = rand()")))
       command.stringify() must equalTo("/d/select.json?table=Entries&scorer=%27_score+%3D+rand%28%29%27")
     }
     "with outputColumns" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", outputColumns=Seq("_id","_key", "_score")))
       command.stringify() must equalTo("/d/select.json?table=Entries&output_columns=_id%2C_key%2C_score")
     }
     "with offset" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", offset=Some(10)))
       command.stringify() must equalTo("/d/select.json?table=Entries&offset=10")
     }
     "with limit" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", limit=Some(15)))
       command.stringify() must equalTo("/d/select.json?table=Entries&limit=15")
     }
     "with cache" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", cache=Some(true)))
       command.stringify() must equalTo("/d/select.json?table=Entries&cache=yes")
     }
     "with no cache" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", cache=Some(false)))
       command.stringify() must equalTo("/d/select.json?table=Entries&cache=no")
     }
     "with matchEscalationThreshold" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", matchEscalationThreshold=Some(-1)))
       command.stringify() must equalTo("/d/select.json?table=Entries&match_escalation_threshold=-1")
     }
     "with queryFlags" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", queryFlags = Some("ALLOW_PRAGMA|ALLOW_COLUMN|ALLOW_UPDATE|ALLOW_LEADING_NOT|NONE")))
       command.stringify() must equalTo("/d/select.json?table=Entries&query_flags=ALLOW_PRAGMA%7CALLOW_COLUMN%7CALLOW_UPDATE%7CALLOW_LEADING_NOT%7CNONE")
     }
     "with queryExpander" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", queryExpander = Some("Thesaurus.synonym")))
       command.stringify() must equalTo("/d/select.json?table=Entries&query_expander=Thesaurus.synonym")
     }
     "with adjuster" >> {
       val command = new SelectCommand(SelectParameters(table="Entries", adjuster = Some("""content @ "groonga" * 5""")))
       command.stringify() must equalTo("/d/select.json?table=Entries&adjuster=content+%40+%22groonga%22+*+5")
     }
     "with drilldown" >> {
       val drilldown = DrilldownParameters("genre")
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns=Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre")
     }
     "with drilldownSortby" >> {
       val drilldown = DrilldownParameters("genre", sortby = Seq("-_nsubrecs", "_key"))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns=Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.sortby=-_nsubrecs%2C_key")
     }
     "with drilldownOffset" >> {
       val drilldown = DrilldownParameters("genre", offset = Some(5))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns=Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.offset=5")
     }
     "with drilldownOutputColumns" >> {
       val drilldown = DrilldownParameters("genre", outputColumns = Seq("_key", "_max"))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns = Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.output_columns=_key%2C_max")
     }
     "with drilldownOutputColumns (no key, but key is to be add." >> {
       val drilldown = DrilldownParameters("genre", outputColumns = Seq("_max"))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns = Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.output_columns=_max%2C_key")
     }
     "with drilldownLimit" >> {
       val drilldown = DrilldownParameters("genre", limit = Some(11))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns = Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.limit=11")
     }
     "with drilldownCalcTypes" >> {
       val drilldown = DrilldownParameters("genre", calcTypes = Seq("MAX","MIN"))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns = Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.calc_types=MAX%2CMIN")
     }
     "with drilldownCalcTarget" >> {
       val drilldown = DrilldownParameters("genre", calcTarget = Some("n_likes"))
       val command = new SelectCommand(SelectParameters(table="Entries", drilldowns = Seq(drilldown)))
       command.stringify() must equalTo("/d/select.json?table=Entries&drilldown%5Bgenre%5D.keys=genre&drilldown%5Bgenre%5D.calc_target=n_likes")
     }
   }
 }
