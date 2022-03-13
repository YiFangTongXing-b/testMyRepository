package com.czf.computer.mapper;

import com.czf.computer.domain.Cart;
import com.czf.computer.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    int insert(Cart cart);     //购物车插入数据

    /**
     * 修改购物车数据中某个商品的数量
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    int updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据，如果该用户的购物车中并没有该商品，则返回null
     */
    Cart findByUidAndPid(int uid, int pid);

    List<CartVO> findVOByUid(int uid);

    Cart findByCid(int cid);

    List<CartVO> findVOByCids(int[] cids);

}
