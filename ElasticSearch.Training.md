Notes from ElasticSearch Core training, Nov 2014, Berlin
------------

* Disable multicast (this is really for development only)
* HTTp protocol, there is no huge difference in performance in comparison with _transport_ protocol. To save bandwitdh, make use of compression.
* ElasticSearch _shard_ is the same as Lucene _index_
* When index is indexed, it goes into single primary shard and all its replicas.
* It will never happen that replica is collocated with its primary shard.
* Number of primary shards cannot be changed once index is created. Number of replica shards can be changed and this is how scalling is done.

* Disable automatic index creation with `action.auto_create_index: false`
* Using explicit `_create` endpoint will cause `409` being returned when document already exists.
* When not using TTL on document, checking t could be disabled.
* GET is realtime - once document is idexed, it could be retrieved. SEARCH is not realtime.

* Refresh is running over all that is not yet persisted in lucene segment (at that time it is recorded in ES transation log)
* Logstash has in its type configuration a setting that changes refresh rate of flushing lucene segments to disk
* Mike McCandles merging lucene segments blog
* SEGMENTS API
* [http://codecapsule.com/2014/02/12/coding-for-ssds-part-1-introduction-and-table-of-contents/]

* Character filter is fired before the tokenizer. This is usefull when 2 words shall not be split by tokenizer and be held as single token.

* It is possible to store the output of analysis. It is called term vector. It is enabled in mapping with `term_vector` property.

* Turn off analysis (using `index` attribute) of fields that are not going to be searched at all (numbers, base64 sequences)
* Numbers has to be indexed to in order to make boundary queries (e.g.  where value is greater than

* `_all` field - concatenate all fields and analyze them
* Embedded Lucene script is the fastest available, it is optimized for Lucene.
* Using `_source` has huge performace impact, this is slow.


* Aggregations operate on field data that need to be loaded into memory. This makes aggregations slower than usual searches.

* LogStash creates bot analyzed and unanalyzed form for each string field.

* If I need tu support partial searches, I need to add also ngrammed field.
* There is percentile aggregation for calculating distributions of data.
* In aggregation, it is possible to specify post time zone conversion to convert date field to partucular zone.

* There are several videos with various aggregations that will be added to ES1.4

* Having array of objects with the same fields will cause search to behave incorrectly if the inner objects are not mapped as nested. See explanation how Lucene internally stores such documents on page 144 of the training materials.
* Nested documents are regular Lucene documents and could be searched.

* Storing geohash together with document allows for extremelly fast queries for something that are next to some location.

* unicast is preferred for production
* the unicast list is just a seed list

* `minimum_master_nodes` shall be interpreted as _number of nodes that are eligible for master_
* `minimum_master_nodes` is a runtime cluster-wide variable that could be changed without restarting cluster when new nodes are added.
* all cluster API requests are executed on master nodes

* Java API Transport client could round-robin nodes that are listed in configuration [http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/client.html]

* with `include.box_type` and `node.box_type` it is possible to relocate shards to specific machines for different purposes. E.g. running search queries on some boxes, while running indexing on perhaps some more performant nodes.

* Also for LogStash it is not desired to use asynchronos shard replication because LS has no backchannel from which it would know that ES is getting overloaded.

* #shards > #nodes

* indexing and searching with using routing on some key is good idea to target smaller number of shards.
* in search query using routing, add also filter with the same key and routing value
* Good idea here is to use aliases with associated filter

* Optimize with the desired result parameter (e.g. `max_num_segments=1`)

