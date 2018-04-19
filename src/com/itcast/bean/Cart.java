package com.itcast.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	//购物项的集合, Map<String ,CartItem > map
	
	private Map<String,CartItem> cartItemMap = new HashMap<>();
	
	//总金额,  double total	
	private double total;
	
	
	/** ${cart.values};JavaBean属性------------?
	 * @return
	 */
	public Collection<CartItem> getValues(){ 
		Collection<CartItem> values = cartItemMap.values();
		return values;
	}
	
	
	//方法一:加入购物车---添加购物项到购物车
	public void addTOCart(CartItem cartItem){
		//1.判断购物车是否有该商品
			//得到cartItem对应商品的pid
			 String pid = cartItem.getProduct().getPid();
		//1.1如果有:	
			//修改购买数量(原来的数量+item.getCount)
			//修改总金额(原来的金额+item.getSubtotal())
			 if(cartItemMap.containsKey(pid)){
				CartItem oldCartItem = cartItemMap.get(pid);
				oldCartItem.setCount(oldCartItem.getCount()+cartItem.getCount());
				total +=cartItem.getSubTotal(); 
			 }else{
				//1.2如果没有:
					//直接put进去 修改总金额(原来的金额+item.getSubtotal())
				 cartItemMap.put(pid, cartItem);
				 total += cartItem.getSubTotal();
			 }
			
		
	}
	//方法二:从购物车中移除---购物项
	public void removeFromCart(String pid){    
		//1.从map中移除指定购物项  
		CartItem cartItem = cartItemMap.remove(pid);
		//  2.修改总金额
		total -= cartItem.getSubTotal();
	}
	
	
	
	//清空购物车
	public void clearCatr(){
		//1.清空map
		cartItemMap.clear();
	    //2.修改总金额=0.0
		total = 0.0;
	}
	
	
	public Map<String, CartItem> getCartItemMap() {
		return cartItemMap;
	}

	public void setCartItemMap(Map<String, CartItem> cartItemMap) {
		this.cartItemMap = cartItemMap;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}


	
	

}
