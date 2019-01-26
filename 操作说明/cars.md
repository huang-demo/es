需要 elasticsearch-plugin install mapper-murmur3
put /cars

{
    "setting": {
        "number_of_shards": 2,
        "number_of_replicas": 1
    },
    "mappings": {
        "trasacations": {
            "properties": {
                "id": {
                    "type": "long"
                },
                "make": {
                    "type": "text",
                    "fielddata": true
                },
                "color": {
                    "type": "text",
                    "fielddata": true,
                    "fields": {
                        "hash": {
                            "type": "murmur3"
                        }
                    }
                },
                "price": {
                    "type": "double"
                },
                "sold": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                }
            }
        }
    }
}

lostash

input{
 jdbc {
        jdbc_driver_library => "X:/MyRepository/mysql/mysql-connector-java/5.1.36/mysql-connector-java-5.1.36.jar"
        jdbc_driver_class => "com.mysql.jdbc.Driver"
        jdbc_connection_string => "jdbc:mysql://localhost:3306/elastic?autoReconnect=true&useSSL=false"
        jdbc_user => "root"
        jdbc_password => "admin"
		jdbc_page_size=>5000
		sql_log_level=>"info"
		lowercase_column_names=>"false"
        schedule => "* * * * *"
        jdbc_default_timezone => "Asia/Shanghai"
		parameters=>{"deleted"=>"0"}
        statement => "SELECT * FROM transactions where deleted=:deleted ;"
		type=>"transactions"
    }
}
output {
    elasticsearch {
		user => "elastic"
		password => "111111"
        index => "cars"
        document_type => "%{type}"
        document_id => "%{id}"
        hosts => ["localhost:9200"]
		
    }
}










