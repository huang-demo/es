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

### fuzzy 模糊搜索

POST /my_index/my_type/_search
{
	"query":{
		"fuzzy":{
			"text":"surprize"
		}
	}
}
##控制错误个数
POST /my_index/_search
{
    "query": {
        "fuzzy": {
            "text": {
                "value": "surprize",
                "fuzziness": 1
            }
        }
    }
}
POST /my_index/_search
{
    "query": {
        "match": {
            "text": {
                "query": "SURPRIZE ME!",
                "fuzziness": "AUTO",
                "operator": "or"
            }
        }
    }
}
POST /project_test/_search
{
    "query": {
        "multi_match": {
            "fields": [
                "name",
                "content"
            ],
            "query": "黄村 5号线",
            "fuzziness": "AUTO"
        }
    }
}

##聚合查询
POST /cars/transactions/_search
{
    "aggs": {
        "agg_color": {
            "terms": {
                "field": "color",
                "size": 2
            }
        }
    },
    "size": 0
}

POST /project_test/_search
{
	"query":{
		"bool":{
			"must":{
				"match":{
					"name":{
						"query":"火车东站 测试",
						"boost":2
					}
				}
			}
		}
	},
	"aggs":{
		"aggs_name":{
			"terms":{
				"field":"name",
				"size":8
			}
		}
	},
	"size":0
}
## avg sum
POST /project_test/projectInfo/_search
{
    "query": {
        "bool": {
            "must": {
                "match_all": {}
            }
        }
    },
    "aggs": {
        "aggs_name": {
            "terms": {
                "field": "name"
            },
            "aggs": {
                "avg_price": {
                    "avg": {
                        "field": "amount"
                    }
                }
            }
        }
    },
    "size":0
}

{
    "query": {
        "bool": {
            "must": {
                "match": {
                    "name": {
                        "query": "车陂 五号线",
                        "operator": "or",
                        "boost": 2
                    }
                }
            }
        }
    },
    "aggs": {
        "agg_name": {
            "terms": {
                "field": "name",
                "size": 5
            },
            "aggs": {
                "avg_amount": {
                    "avg": {
                        "field": "amount"
                    }
                },
                "agg_type": {
                    "terms": {
                        "field": "projectType"
                    }
                }
            }
        }
    },
    "size": 0
}


###为每个汽车生成商计算最低和最高的价格
{
    "aggs": {
        "color": {
            "terms": {
                "field": "color"
            },
            "aggs": {
                "agg_price": {
                    "avg": {
                        "field": "price"
                    }
                },
                "make": {
                    "terms": {
                        "field": "make"
                    },
                    "aggs": {
                        "agg_minprice": {
                            "min": {
                                "field": "price"
                            }
                        },
                        "agg_maxPrice": {
                            "max": {
                                "field": "price"
                            }
                        }
                    }
                }
            }
        }
    },
    "size": 0
}

### 直方图 histogram  时间跨度interval,条形图统计各区间总和
{
    "size": 0,
    "aggs": {
        "agg_price": {
            "histogram": {
                "field": "price",
                "interval": 20000
            },
            "aggs": {
                "agg_sumPrice": {
                    "sum": {
                        "field": "price"
                    }
                }
            }
        }
    }
}


## extended_stats 方差、标准差,平均价... 最受欢迎的10种品牌信息
{
    "aggs": {
        "agg_make": {
            "terms": {
                "field": "make",
                "size": 10
            },
            "aggs": {
                "stat_price": {
                    "extended_stats": {
                        "field": "price"
                    }
                }
            }
        }
    },
    "size": 0
}

## 按月统计销量
{
    "size": 0,
    "aggs": {
        "sales_month": {
            "date_histogram": {
                "field": "sold",
                "interval": "month",
                "format": "yyyy-MM-dd"
            }
        }
    }
}
####强制没有数据也返回
{
    "size": 0,
    "aggs": {
        "agg_month": {
            "date_histogram": {
                "field": "sold",
                "interval": "month",
                "format": "yyyy-MM-dd",
                "min_doc_count": 0,
                "extended_bounds": {
                    "min": "2014-01-01",
                    "max": "2014-12-31"
                }
            }
        }
    }
}
{
    "size": 0,
    "aggs": {
        "quarter_sales": {
            "date_histogram": {
                "field": "sold",
                "interval": "quarter",
                "format": "yyyy-MM-dd",
                "min_doc_count": 0,
                "extended_bounds": {
                    "min": "2014-01-1",
                    "max": "2014-12-31"
                }
            },
            "aggs": {
                "agg_make": {
                    "terms": {
                        "field": "make"
                    },
                    "aggs": {
                        "avg_price": {
                            "avg": {
                                "field": "price"
                            }
                        },
                        "sum_price": {
                            "sum": {
                                "field": "price"
                            }
                        }
                    }
                }
            }
        }
    }
}