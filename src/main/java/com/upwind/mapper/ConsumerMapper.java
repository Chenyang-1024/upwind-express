package com.upwind.mapper;

import com.upwind.pojo.Consumer;
import com.upwind.pojo.ConsumerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    long countByExample(ConsumerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int deleteByExample(ConsumerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int insert(Consumer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int insertSelective(Consumer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    List<Consumer> selectByExample(ConsumerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    Consumer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int updateByExampleSelective(@Param("record") Consumer record, @Param("example") ConsumerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int updateByExample(@Param("record") Consumer record, @Param("example") ConsumerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int updateByPrimaryKeySelective(Consumer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer
     *
     * @mbg.generated Sat Oct 29 16:34:20 CST 2022
     */
    int updateByPrimaryKey(Consumer record);
}