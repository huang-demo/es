
    
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
                "projectname": {
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
获取mapping 
GET /_mapping/user,projectInfo
##mget
GET /_mget
{
    "docs": [
        {
            "_index": "project_test",
            "_type": "projectInfo",
            "_id": 1
        },
        {
            "_index": "user",
            "_type": "userinfo",
            "_id": 1
        }
    ]
}

##indexA=>indexB
1.给indexA建立别名
put /indexA/_alias/aliasA
2.创建indexB
...
3.执行以下
 POST /_aliases
{
    "actions": [
        {
            "remove": {
                "index": "indexA",
                "alias": "aliasA"
            }
        },
        {
            "add": {
                "index": "indexB",
                "alias": "aliasA"
            }
        }
    ]
}

查询name=张*翼
POST /project_test/person/_search
 {
   "query":{
     "match_phrase": {
       "name":{ 
         "query": "张,翼",
         "slop":1
          
       }
     }
   }
 }

 POST /project_test/person/_search
 {
   "query":{
     "term": {
       "name":"张"
     }
   },
   "from": 0,
   "size": 2
 }