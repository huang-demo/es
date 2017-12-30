##查询所有
post /_search
{
    "query": {
        "match_all": {}
    },
    "size":20,
    "from":0
}
##查询名称包含车陂排除
post /_search
{
    "query": {
        "match": {"projectname":"车陂"}
    },
    "size":20,
    "from":0
}
##查询名称包含车陂排除 奥林匹克中心站
post /_search
{
    "query": {
        "bool": {
            "must": {
                "match": {
                    "projectname": "车陂"
                }
            },
            "must_not": {
                "match": {
                    "projectname": "奥林匹克中心站"
                }
            }
        }
    }
}

##查询专业0301
post /_search
{
    "query": {
        "term": {
            "specialtys": "0301"
        }
    }
}

POST /_search
{
  "query": {
   "terms": {
     "specialtys": ["0301","0302"]
   }
  }
}
##区间查询 rang
{
    "query": {
        "range": {
            "projectamount": {
                "gt": 35591885,
                "lte": 35591886
            }
        }
    }
}
## 排除字段为空 exists
{
  "query": {
   "exists":{
     "field":"construction"
   }
  }
}

## bool 过滤 projectname like 车陂 and projectname != 奥林匹克中心站 and (specialtys = 0301 or specialty =0303)
{
  "query": {
   "bool": {
     "must":{
       "match":{
         "projectname":"车陂"
       }
     },
     "must_not": {
       "match":{
         "projectname":"奥林匹克中心站"
       }
     },"should": [
       {"term": {
        "specialtys": "0301"
       }},
       {"term":{
         "specialtys":"0303"
       }}
     ]
   }
  }
}
##多字段查询
POST /_search
{
 "query": {
   "multi_match":{
     "query":"车陂",
     "fields":["projectname","region"]
   }
 }
}

##排序
{
    "query": {
        "bool": {
            "filter": {
                "term": {
                    "specialtys": "0301"
                }
            }
        }
    },
    "sort": {
        "projectamount": "desc"
    }
}
##指定相应字段
POST /project/projectBaseInfo/_search
{
    "query": {
        "match":{
          "projectname":"车陂"
        }
    },
    "_source": ["projectname","projectamount"]
}

##match 会对查询进行分析 拆成多个简单关键字 进行合并
{
	"query":{
		"match":{
			"name":"五号线"
		}
	}
}
post /project_test/projectInfo/_search
{
    "query": {
        "match": {
            "name": "五号线 车陂"
        }
    }
}
###霰弹策略(shotgun approach) operator 默认是or 
post /project_test/projectInfo/_search
{
	"query":{
		"match":{
			"name":{
				"query":"五号线 车陂",
				"operator":"and"
			}
		}
	}
}
##控制 百分比 或者关键字个数
{
    "query": {
        "match": {
            "name": {
                "query": "五号线 车陂",
                "minimum_should_match": "75%"
            }
        }
    }
}

## 设置关键字权重 boost 关键字 默认为1 原有得分 * x
{
    "query": {
        "bool": {
            "filter": {
                "match": {
                    "name": {
                        "query": "五号线"
                    }
                }
            },
            "should": [
                {
                    "match": {
                        "name": {
                            "query": "珠江新城",
                            "boost": 3
                        }
                    }
                },
                {
                    "match": {
                        "name": {
                            "query": "猎德",
                            "boost": 2
                        }
                    }
                }
            ]
        }
    }
}

#多字段查询
{
	"query":{
		"multi_match":{
			"query":"车陂",
			"fields":["name","content"]
		}
	}
}
##最佳字段搜索 ：best_fields同样是以字段为中心
{
    "query": {
        "multi_match": {
            "query": "test abc",
            "type": "best_fields",
            "fields": [
                "name",
                "content"
            ]
        }
    }
}

{
	"query":{
		"multi_match":{
			"query":"test abc",
			"type":"most_fields",
			"fields":["name","content"]
		}
	}
}



