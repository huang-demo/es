https://github.com/medcl/elasticsearch-analysis-ik

##elasticsearch 配置
###master:
    cluster.name: DEM
    node.name: master
    network.host: 127.0.0.1
    http.port: 9200
    http.cors.enabled: true 
    http.cors.allow-origin: "*"
    node.master: true
    node.data: true
####saler
    cluster.name: DEM
    node.name: master
    network.host: 127.0.0.1
    http.port: 8200
    discovery.zen.ping.unicast.hosts: ["127.0.0.1"]
    node.master: false
    node.data: true
    http.cors.enabled: true
    http.cors.allow-origin: "*"
    
######启动 elasticsearch-head :npm run start
######idea springboot启动: mvn spring-boot:run
##ElasticSearch
###创建索引 put   
url:localhost:9200/people
req:
{
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 1
    },
    "mappings": {
        "man": {
            "properties": {
                "name": {
                    "type": "text"
                },
                "country": {
                    "type": "keyword"
                },
                "age": {
                    "type": "integer"
                },
                "date": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                }
            }
        },"women":{}
    }
}
{
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 2
    },
    "mappings": {
        "projectBaseInfo": {
            "properties": {
                "projectId": {
                    "type": "integer"
                },
                "pid": {
                    "type": "integer"
                },
                "code": {
                    "type": "text"
                },
                "province": {
                    "type": "text"
                },
                "city": {
                    "type": "text"
                },
                "region": {
                    "type": "text"
                },
                "projectamount": {
                    "type": "double"
                },
                "projectName": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "constructionUnits": {
                     "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "isDeleted": {
                    "type": "integer"
                },
                "contactWay": {
                    "type": "text"
                },
                "contacts": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "designunit": {
                   "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "remark": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "pricingYear": {
                    "type": "integer"
                },
                "proUse": {
                    "type": "text"
                },
                "startWorkDate": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                },
                "endWorkDate": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                },
                "createon": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                }
            }
        }
    }
}
###保存数据 post
指定索引
http://localhost:9200/people/man/1
//系统创建
http://localhost:9200/people/man
req
{
"name":"demo",
"country":"china",
"age":10,
"date":"2010-10-10"
}


#--------------------------------

{
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 2
    },
    "mappings": {
        "projectInfo": {
            "properties": {
                "projectId": {
                    "type": "text"
                },
                "projectName": {
                   "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
                "pid": {
                    "type": "text"
                },
                "createDate": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                },
                "content": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "search_analyzer": "ik_max_word"
                },
				 "user": {
                    "type": "text"
                   
                }, 
				"phone": {
                    "type": "text"
                  
                }
            }
        }
    }
}


