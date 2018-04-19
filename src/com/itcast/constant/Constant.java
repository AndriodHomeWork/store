package com.itcast.constant;

/**
 * @author niki_yeun
 *
 */
public class Constant {

	/**
	 * 用户的状态
	 */
	public final static int USER_NOT_ACTIVE = 0;
	public final static int USER_ACTIVE =1;
	
	/**
	 * 商品是否热门 is_hot;  //是否热门  1:热门    0:不热门
	 */
	public final static Integer PRODUCT_IS_HOT = 1;
	public final static Integer PRODUCT_IS_NOT_HOT = 0;
	/**
	 * 一页显示的商品数量
	 */
	public final static  int PRODUCT_PAGE_SIZE = 12;
	
	
	
	/**
	 * private Integer state;//订单状态 0:未付款	1:已付款	2:已发货	3.已完成
	 */
	public final static Integer UN_PAY = 0;
	public final static Integer PAYED = 1;
	public final static Integer DELIVERED = 2;
	public final static Integer FISHINED = 3;
	
	
	
	/**
	 * 购物车分页展示中,一页显示的order数量
	 */
	public static final int ORDER_PAGE_SIZE = 2;
	
	
}
