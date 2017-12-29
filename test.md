##查看分词效果
#### ik_smart就是最少切分，
#### ik_max_word则为细粒度的切分
GET _analyze
{
  "analyzer":"ik_max_word",
  "text":"中华人民共和国国歌"
}


PUT test_idex
{
    "number_of_shards": "2",
    "number_of_replicas": "1",
    "analysis": {
        "analyzer": {
            "default": {
                "tokenizer": "ik_max_word"
            },
            "pinyin_analyzer": {
                "tokenizer": "my_pinyin"
            }
        },
        "tokenizer": {
            "my_pinyin": {
                "keep_separate_first_letter": "false",
                "lowercase": "true",
                "type": "pinyin",
                "limit_first_letter_length": "16",
                "keep_original": "true",
                "keep_full_pinyin": "true"
            }
        }
    }
}

PUT /test_index/test/_mapping
{
    "_all": {
        "analyzer": "ik_max_word"
    },
    "properties": {
        "name": {
            "type": "text",
            "analyzer": "ik_max_word",
            "include_in_all": true,
            "fields": {
                "pinyin": {
                    "type": "text",
                    "term_vector": "with_positions_offsets",
                    "analyzer": "pinyin_analyzer",
                    "boost": 10
                }
            }
        }
    }
}

POST test_index/test/_search?q=name.pinyin:ldh