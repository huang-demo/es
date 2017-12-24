index=>project_test
                    =>projectInfo
                    =>person
##es 5.63
{
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 1
    },
    "mappings": {
        "projectInfo": {
                       "properties": {
                           "id": {
                               "type": "long"
                           },
                           "pid": {
                               "type": "integer"
                           },
                           "projecttype": {
                               "type": "integer"
                           },
                           "amount": {
                               "type": "double"
                           },
                           "name": {
                               "type": "text",
                               "analyzer": "ik_smart",
                               "search_analyzer": "ik_max_word"
                           },
                           "createtime": {
                               "type": "date",
                               "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                           },
                           "updatetime": {
                               "type": "date",
                               "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                           },
                           "content": {
                               "type": "text",
                               "analyzer": "ik_smart",
                               "search_analyzer": "ik_max_word"
                           }
                       } 
               },"person":{
                                  "properties": {
                                      "id": {
                                          "type": "long"
                                      },"age":{
                          				"type":"integer"
                          			},
                                      "name": {
                                          "type": "text",
                                          "analyzer": "ik_smart",
                                          "search_analyzer": "ik_max_word"
                                      },
                                      "createtime": {
                                          "type": "date",
                                          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                                      },
                                      "updatetime": {
                                          "type": "date",
                                          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||yyyy-MM-dd'T'HHmmss.SSSZ"
                                      },
                                      "address": {
                                          "type": "text",
                                          "analyzer": "ik_smart",
                                          "search_analyzer": "ik_max_word"
                                      }
                                  } 
                          }
    }
}
配置 jdbc 
input {
    jdbc {
        jdbc_driver_library => "X:/MyRepository/mysql/mysql-connector-java/5.1.36/mysql-connector-java-5.1.36.jar"
        jdbc_driver_class => "com.mysql.jdbc.Driver"
        jdbc_connection_string => "jdbc:mysql://localhost:3306/elastic?autoReconnect=true&useSSL=false"
        jdbc_user => "root"
        jdbc_password => "admin"
        schedule => "* * * * *"
        jdbc_default_timezone => "Asia/Shanghai"
        statement => "SELECT * FROM projectinfo;"
		type=>"projectInfo"
    }
	 jdbc {
        jdbc_driver_library => "X:/MyRepository/mysql/mysql-connector-java/5.1.36/mysql-connector-java-5.1.36.jar"
        jdbc_driver_class => "com.mysql.jdbc.Driver"
        jdbc_connection_string => "jdbc:mysql://localhost:3306/elastic?autoReconnect=true&useSSL=false"
        jdbc_user => "root"
        jdbc_password => "admin"
        schedule => "* * * * *"
        jdbc_default_timezone => "Asia/Shanghai"
        statement => "SELECT * FROM person;"
		type=>"person"
    }
}
output {
    elasticsearch {
        index => "project_test"
        document_type => "%{type}"
        document_id => "%{id}"
        hosts => ["localhost:9200"]
    }
}
启动命令
logstash -f xxx

