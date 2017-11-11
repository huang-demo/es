慕课网课程练习

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


