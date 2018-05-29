package com.dem.es.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtil {
    public static void main(String[] args) {
        String sql = getSql();
//        String sql = "select * from tProjectBaseInfo t left join t3 t3 on t3.projectId = t.id where t.id=2 union  select * from tProjectBaseInfo t2 where t2.id=1254";
//期望 select * from tProjectBaseInfo t left join t3 t3 on t3.projectId = t.id where t.id=1 and t.perCode in(1,2,3) union  select * from tProjectBaseInfo t2 where t2.id=11 and t2.perCode in(1,2,3)
        List<String> tableNames = new ArrayList<>();
        tableNames.add("tprojectbaseinfo");
        List<String> codeList = new ArrayList<>();
        codeList.add("1");
        codeList.add("2");
        codeList.add("3");
        String s = addCondition(sql, tableNames, "perCode", codeList);
        System.out.println(s);


    }

    /**
     * 添加条件
     *
     * @param sql
     * @param matchTable
     * @param columName
     * @param dataCodes
     * @return
     */
    public static String addCondition(String sql, List<String> matchTable, String columName, List<String> dataCodes) {

        sql = sql.replace("  ", " ").toLowerCase();
        Pattern scriptPattern = Pattern.compile("(.*?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = null;
        String reg = "";
        Set<String> alisName = getTbAlias(sql, matchTable);
        for (String s : alisName) {
            reg = "\\s" + s + "\\s+(.*?)where ";
            scriptPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            matcher = scriptPattern.matcher(sql);
            while (matcher.find()) {
                String codition = matcher.group(0);
                codition = codition.replace("where", "where " + s + "." + columName + " IN (" + ListUtil.list2Str(dataCodes) + ") and  ");
                sql = scriptPattern.matcher(sql).replaceAll(codition);
            }
        }
        return sql;
    }

    /**
     * 获取表格别名
     */

    public static Set<String> getTbAlias(String sql, List<String> matchTable) {
        Set<String> list = new HashSet<>();
        Pattern scriptPattern = Pattern.compile("(.*?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = null;
        String reg = "";

        for (String table : matchTable) {
            reg = "\\s" + table + "\\s+(.*?)where ";
            //忽略大小写
            scriptPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            matcher = scriptPattern.matcher(sql);
            while (matcher.find()) {
                String temp = matcher.group(1).trim();
                String[] split = temp.trim().split(" ");
                list.add("as".equalsIgnoreCase(split[0]) ? split[1] : split[0]);
            }

        }
        return list;
    }

    /**
     * 获取表名
     *
     * @param sql
     * @return
     */
    public static List<String> matchTableName(String sql) {
        Pattern p = Pattern.compile("(?i)(?<=(?:from|into|update|join)\\s{1,1000})(\\w+)");
        Matcher m = p.matcher(sql);
        List<String> tables = new ArrayList<String>();
        while (m.find()) {
            tables.add(m.group());
        }
        return tables;
    }

    public static String getSql() {
        return " select ta.projectRate,ta.currentPhase,ta.thirdcategoryCode,ta.subcategoryCode,ta.categoryCode,ta.isPlanBuild,ta.preparationtime,ta.id,ta.projectno,ta.projectname, " +
                " ta.attrName,ta.projectamount,ta.calculatingAperture,ta.lineNo,ta.lineName,ta.specialtys,ta.specialtyName,ta.phaseName,ta.userId,ta.userName,ta.chName, ta.deptName," +
                " ta.branchName,ta.proResourceName,count(1) as tmpStr from (select * from (select ss.*,group_concat(DISTINCT(b.`name`) separator ',') as branchName,group_concat(DISTINCT(r.name) separator ',') as proResourceName " +
                " ,z.totalDate,z.totalsDate,z.fymxDate,z.fbfxDate from (" +
                " select t.projectRate,t.projectStatus,t.currentPhase,t.thirdcategoryCode,t.subcategoryCode,t.categoryCode,t.isPlanBuild,t.preparationtime,t.id,t.projectno,t.projectname,group_concat(DISTINCT(pc.attrName) separator ',') as attrName,projectamount,t.calculatingAperture,t.lineNo,l.`name` as lineName,t.specialtys,s.`name` as specialtyName,p.`name` as phaseName,us.id as userId,us.userName,us.chName, us.deptName " +
                " from tProjectBaseInfo t  left join tLineInfo l on t.lineNo = l.code" +
                " left join tProjectSpecialty s on t.specialtys = s.code" +
                " left join tProjectPhase p on t.currentPhase = p.id" +
                " left join (select u.id,u.chName,u.userName,d.deptName from tDeptUser du LEFT JOIN  tUser u on du.userId = u.id LEFT JOIN tDepartMent d on du.deptId = d.id ) us" +
                " on t.createby = us.id left join (select c.projectId,concat(c.fieldName,':',c.`value`) as attrName from tProjectCategoryAttrValue c ) pc on t.id = pc.projectId" +
                " where t.projectType = 0 and t.isDeleted = 0 group by t.id ) ss" +
                " left join tProBranch b on ss.id = b.projectId" +
                " left join (select projectid,max(totalDate) as totalDate,max(totalsDate) as totalsDate,max(fymxDate) as fymxDate,max(fbfxDate) as fbfxDate from prjDateStatus group by projectid) z on ss.id = z.projectid" +
                " left join tProResourceItem r on ss.id = r.projectId group by ss.id ) s where 1=1 ";
    }
}
