# Tools for Elasticsearch

## Json2CSV

It will convert json returned as Elasticsearch query result to csv.

For example,
<pre>
{
  "took" : 7,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 120,
      "relation" : "eq"
    },
    "max_score" : 1.0,
    "hits" : [
      {
        "_index" : "index1",
        "_type" : "iot",
        "_id" : "4fa4646e-f80e-46a7-b775-2cca76c129a4",
        "_score" : 1.0,
        "_source" : {
          "timestamp" : "2019-09-05T12:01:06+0900",
          "source" : "Device1",
          "temperature" : 24.59,
          "humidity" : 74.39,
          "light" : 244.0,
          "noise" : 245.76
        }
      },
      {
        "_index" : "index1",
        …
</pre>
will be converted to
<pre>
timestamp,source,temperature,humidity,light,noise
2019-09-05T12:01:06+0900,Device1,24.59,74.39,244.0,245.76
...
</pre>


Command for Windows is as follows.

<pre>
java -classpath bin;lib\gson-2.8.5.jar com.hito4t.elasticsearch.Json2Csv [-i &lt;input file (json)&gt;] [-o &lt;output file (csv)&gt;]
</pre>

Command for Linux is as follows.

<pre>
java -classpath bin:lib/gson-2.8.5.jar com.hito4t.elasticsearch.Json2Csv [-i &lt;input file (json)&gt;] [-o &lt;output file (csv)&gt;]
</pre>

If `-i` option is omitted, input from standard input.
If `-o` option is omitted, output to standard output.


