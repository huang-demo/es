##定义发音分析器
1.安装分析器 elasticsearch-plugin install analysis-phonetic
PUT /my_index
{
  "settings": {
    "analysis": {
      "filter": {
        "dbl_metaphone": {
          "type":    "phonetic",
          "encoder": "double_metaphone"
        }
      },
      "analyzer": {
        "dbl_metaphone": {
          "tokenizer": "standard",
          "filter":    "dbl_metaphone"
        }
      }
    }
  }
}

PUT /my_index/_mapping/my_type
{
  "properties": {
    "name": {
      "type": "text",
      "fields": {
        "phonetic": { 
          "type":     "text",
          "analyzer": "dbl_metaphone"
        }
      }
    }
  }
}

PUT /my_index/my_type/1
{
  "name": "John Smith"
}

PUT /my_index/my_type/2
{
  "name": "Jonnie Smythe"
}

GET /my_index/my_type/_search
{
  "query": {
    "match": {
      "name.phonetic": {
        "query": "Jahnnie Smeeth",
        "operator": "and"
      }
    }
  }
}