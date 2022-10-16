package com.upwind.mapper;

import com.upwind.pojo.Receivewise;
import com.upwind.pojo.ReceivewiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReceivewiseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    long countByExample(ReceivewiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int deleteByExample(ReceivewiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int insert(Receivewise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int insertSelective(Receivewise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    List<Receivewise> selectByExample(ReceivewiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    Receivewise selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int updateByExampleSelective(@Param("record") Receivewise record, @Param("example") ReceivewiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int updateByExample(@Param("record") Receivewise record, @Param("example") ReceivewiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int updateByPrimaryKeySelective(Receivewise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table receivewise
     *
     * @mbg.generated Sun Oct 16 16:52:45 CST 2022
     */
    int updateByPrimaryKey(Receivewise record);
}