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