package com.zilu.validate;

import java.util.HashMap;
import java.util.List;

public class PhoneDist {
	DistNode[] roots;
	
	public PhoneDist(List<String> phones) {
		roots = new DistNode[10];
		buildDist(phones);
	}
	
	
	void buildDist(List<String> phones) {
		for (String phone : phones) {
			buildDist(phone);
		}
	}

	void buildDist(String phone) {
		char[] nums = phone.toCharArray();
		int index = nums[0] - 48;
		if (roots[index] == null) {
			roots[index] = new DistNode();
		}
		DistNode node = roots[index];
		int i;
		for (i = 1; i < nums.length ; i ++) {
			node = node.addSubNode(nums[i]);
		}
	}
	
	public boolean find(String phone) {
		char[] nums = phone.toCharArray();
		int index = nums[0]- 48;
		DistNode node = roots[index];
		for (int i = 1; i < nums.length ; i ++) {
			if (node == null) {
				return false;
			}
			node = node.getSubCode(nums[i]);
		}
		return true;
	}
 
	class DistNode extends HashMap<Character, DistNode> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -8941545041018060864L;

		public DistNode addSubNode(char cindex) {
			DistNode node = new DistNode();
			put(cindex, node);
			return node;
		}
		
		
		public DistNode getSubCode(char cindex) {
			return get(cindex);
		}
	}
	
	
}
