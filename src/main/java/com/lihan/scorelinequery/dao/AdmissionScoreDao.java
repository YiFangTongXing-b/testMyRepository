package com.lihan.scorelinequery.dao;


import com.lihan.scorelinequery.entity.AdmissionScore;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdmissionScoreDao {
    //查询
    @Select({"<script>",
            "SELECT *,provinces.name as province_name FROM admission_score LEFT JOIN provinces ON admission_score.pid=provinces.pid WHERE 1=1",
            "<when test=\"school_name!='null'\">",
            "AND school_name LIKE '%${school_name}%'",
            "</when>",
            "<when test=\"province_name!='null'\">",
            "AND provinces.name LIKE '%${province_name}%'",
            "</when>",
            "<when test=\"year!=null\">",
            "AND year = #{year}",
            "</when>",
            "<when test=\"major!='null'\">",
            "AND major LIKE '%${major}%'",
            "</when>",
            "<when test=\"kind!='null'\">",
            "AND kind LIKE '%${kind}%'",
            "</when>",
            "</script>"})
    List<AdmissionScore> SelectAdmissionScores(@Param("school_name") String school_name, @Param("province_name") String province_name,@Param("year") Integer year,
                                               @Param("major") String major, @Param("kind") String kind);
    @Select("SELECT * FROM admission_score WHERE pid=#{pid} AND school_name=#{school_name} AND year=#{year} AND major=#{major} AND kind=#{kind}")
    AdmissionScore SelectAdmissionScore(@Param("pid") Integer pid,@Param("school_name") String school_name,
                                              @Param("year") Integer year, @Param("major") String major, @Param("kind") String kind);

    //删除
    @Delete("DELETE FROM admission_score WHERE admission_id=#{admission_id}")
    void DeleteAdmissionScore(Integer admission_id);

    //增加
    @Insert("INSERT INTO admission_score(pid,school_id,school_name,year,major,kind,least_score,high_score,avg_score,least_rank,num) VALUES(#{pid},#{school_id},#{school_name},#{year},#{major},#{kind},#{least_score},#{high_score},#{avg_score},#{least_rank},#{num})")
    void InsertAdmissionScore(@Param("pid") Integer pid, @Param("school_id") Integer school_id,
                              @Param("school_name") String school_name, @Param("year") Integer year,
                              @Param("major") String major, @Param("kind") String kind,
                              @Param("least_score") Integer least_score, @Param("high_score") Integer high_score,
                              @Param("avg_score") Integer avg_score, @Param("least_rank") Integer least_rank,
                              @Param("num") Integer num);

    //修改
    @Update("UPDATE admission_score SET pid=#{pid},school_id=#{school_id},school_name=#{school_name},year=#{year},major=#{major},kind=#{kind},least_score=#{least_score},high_score=#{high_score},avg_score=#{avg_score},least_rank=#{least_rank},num=#{num} WHERE admission_id = #{admission_id}")
    void UpdateAdmissionScore(@Param("admission_id")Integer admission_id,@Param("pid") Integer pid, @Param("school_id") Integer school_id,
                              @Param("school_name") String school_name, @Param("year") Integer year,
                              @Param("major") String major, @Param("kind") String kind,
                              @Param("least_score") Integer least_score, @Param("high_score") Integer high_score,
                              @Param("avg_score") Integer avg_score, @Param("least_rank") Integer least_rank,
                              @Param("num") Integer num);
}
